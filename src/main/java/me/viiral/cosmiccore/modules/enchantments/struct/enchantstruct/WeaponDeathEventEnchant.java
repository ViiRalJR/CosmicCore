package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;


import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityDeathEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class WeaponDeathEventEnchant extends Enchantment {

    public WeaponDeathEventEnchant(String name, EnchantTier tier, int max, EnchantType type, String... description) {
        super(name, tier, false, max, type, description);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.getKiller() == null) return;
        Player killer = victim.getKiller();
        if (killer.getItemInHand() == null) return;
        ItemStack itemStack = killer.getItemInHand();

        if (!this.getType().getItems().contains(itemStack.getType())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        EnchantProcEvent procEvent = new EnchantProcOnEntityDeathEvent(killer, victim, this, enchantedItemBuilder.getEnchantmentLevel(this));
        Bukkit.getPluginManager().callEvent(procEvent);

        if(procEvent.isCancelled()) return;
        this.runEntityDeathEvent(event, killer, victim, enchantedItemBuilder);
    }

    public abstract void runEntityDeathEvent(EntityDeathEvent event, Player killer, LivingEntity victim, EnchantedItemBuilder enchantedItemBuilder);
}
