package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Telepathy extends BlockBreakEventEnchant {

    public Telepathy() {
        super("Telepathy", EnchantTier.UNIQUE, 4, EnchantType.TOOL, "Automatically places blocks broken by tools in your inventory.");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        event.setCancelled(true);
        for (ItemStack drop : event.getBlock().getDrops()) {
            event.getPlayer().getInventory().addItem(drop);
        }
        event.getBlock().setType(Material.AIR);
    }
}
