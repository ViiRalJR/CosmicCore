package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt extends BlockBreakEventEnchant {

    public AutoSmelt() {
        super("Auto Smelt", EnchantTier.SIMPLE, 1, EnchantType.PICKAXE, "&fSmelts mined ores into ingots.");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        Block block = event.getBlock();

        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        World world = location.getWorld();
        if (world == null) return;

        Material blockType = block.getType();
        ItemStack dropItem;

        switch (blockType) {
            case IRON_ORE -> dropItem = new ItemStack(Material.IRON_INGOT);
            case GOLD_ORE -> dropItem = new ItemStack(Material.GOLD_INGOT);
            default -> {
                return;
            }
        }

        world.dropItem(location, dropItem);

        event.setCancelled(true);
        block.setType(Material.AIR);
    }

}
