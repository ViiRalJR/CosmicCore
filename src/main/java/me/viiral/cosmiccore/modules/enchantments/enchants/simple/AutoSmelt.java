package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt extends BlockBreakEventEnchant {

    public AutoSmelt() {
        super("Auto Smelt", EnchantTier.SIMPLE, 1, EnchantType.PICKAXE, "&fSmelts mined ores into ingots.");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        Location location = event.getBlock().getLocation().add(0.5, 0.5, 0.5);

        switch (event.getBlock().getType()) {
            case IRON_ORE:
                location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT));
                break;
            case GOLD_ORE:
                location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
                break;
            default:
                return;
        }

        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
    }
}
