package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public abstract class WeaponDamageEventEnchant extends Enchantment {

    public WeaponDamageEventEnchant(String name, EnchantTier tier, int max, EnchantType type, String... description) {
        super(name, tier, false, max, type, description);
    }

    @EventHandler
    public void damageEvent(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamage() <= 0) return;
        if (!(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();
        ItemStack itemStack = attacker.getItemInHand();

        if (!ItemUtils.isWeapon(itemStack)) return;

        LivingEntity victim = (LivingEntity) event.getEntity();

        if (!PVPUtils.canPVP(attacker, victim)) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        EnchantProcEvent procEvent = new EnchantProcOnEntityEvent(attacker, victim, this, enchantedItemBuilder.getEnchantmentLevel(this));
        Bukkit.getPluginManager().callEvent(procEvent);

        if (procEvent.isCancelled()) return;

        if (this.getTier() == EnchantTier.SOUL && !CosmicCore.getInstance().getSoulManager().isInSoulMode(attacker)) return;

        this.runEntityDamageByEntityEvent(event, victim, attacker, enchantedItemBuilder);
    }

    public abstract void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder);
}
