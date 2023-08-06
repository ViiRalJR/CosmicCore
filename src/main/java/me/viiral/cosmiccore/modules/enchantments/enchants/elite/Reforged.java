package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Reforged extends WeaponDamageEventEnchant {

    public Reforged() {
        super("Reforged", EnchantTier.ELITE,10, EnchantType.WEAPONS_AND_TOOLS, "Protects durability, items will take longer to break.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        ItemStack itemStack = attacker.getItemInHand();
        itemStack.setDurability((short) (itemStack.getDurability() - 2));
        attacker.setItemInHand(itemStack);
    }


    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        ItemStack itemStack = event.getPlayer().getItemInHand();

        if (itemStack == null) return;
        if (!this.getType().getItems().contains(itemStack.getType())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        itemStack.setDurability((short) (itemStack.getDurability() - 10));
        event.getPlayer().setItemInHand(itemStack);
    }

}
