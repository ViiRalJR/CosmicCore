package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;

public class Experience extends BlockBreakEventEnchant {

    @ConfigValue
    private double procChance = 0.05;

    public Experience() {
        super("Experience", EnchantTier.SIMPLE, 5, EnchantType.TOOL, "Gives more exp when mining blocks. ");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.EXPERIENCE_ORB);
        }
    }
}
