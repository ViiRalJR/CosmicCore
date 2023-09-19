package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ArrowBreak extends Enchantment {

    public ArrowBreak(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    @EventHandler
    public void onIncomingArrowDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || event.getDamage() <= 0) return;

        if (!(event.getEntity() instanceof Player victim) || !(event.getDamager() instanceof Arrow)) return;

        ItemStack itemStack = victim.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR || !this.getType().getItems().contains(itemStack.getType())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);
        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        if (super.isOnCooldown(victim)) return;

        event.setCancelled(true);
        super.registerCooldown(victim, 20);
    }

}
