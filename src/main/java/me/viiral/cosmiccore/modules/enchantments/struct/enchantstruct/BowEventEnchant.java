package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityEvent;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.WeakShotCooldown;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import me.viiral.cosmiccore.utils.CooldownUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import static me.viiral.cosmiccore.modules.NbtTags.WEAK_SHOT_CACHE_ID;


public abstract class BowEventEnchant extends Enchantment {

    protected boolean hasEnoughPower = false;

    public BowEventEnchant(String name, EnchantTier tier, int max, EnchantType type, String... description) {
        super(name, tier, false, max, type, description);
    }

    @EventHandler
    public void damageEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getDamager();

        if (!(arrow.getShooter() instanceof Player)) return;
        if (event.getDamage() <= 0) return;

        Player victim = (Player) event.getEntity();
        Player attacker = (Player) arrow.getShooter();

        if (!PVPUtils.canPVP(victim, attacker)) return;

        ItemStack itemStack = attacker.getItemInHand();

        if (itemStack == null) return;
        if (!this.getType().getItems().contains(itemStack.getType())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        if (!hasEnoughPower) return;

        EnchantProcEvent procEvent = new EnchantProcOnEntityEvent(attacker, victim, this, enchantedItemBuilder.getEnchantmentLevel(this));
        Bukkit.getPluginManager().callEvent(procEvent);

        if (procEvent.isCancelled()) return;

        this.runEntityDamageByEntityEvent(event, victim, attacker, arrow, enchantedItemBuilder);
    }

    public abstract void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, Player victim, Player attacker, Arrow arrow, EnchantedItemBuilder enchantedItemBuilder);

    @EventHandler(priority = EventPriority.LOW)
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player shooter = (Player) ((Arrow) event.getProjectile()).getShooter();
        ItemStack itemStack = shooter.getItemInHand();

        if (!ItemUtils.isEnchantable(itemStack)) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantments()) return;

        if (event.getForce() >= 0.75D) {
            this.hasEnoughPower = true;
            return;
        }



        this.hasEnoughPower = false;
        if (!CooldownUtils.isCooldownActive(shooter, WEAK_SHOT_CACHE_ID)) {
            CooldownUtils.registerCooldown(shooter, new WeakShotCooldown());
            EnchantLanguage.WEAK_SHOT.send(shooter);
        }
    }
}
