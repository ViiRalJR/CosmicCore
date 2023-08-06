package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.event.block.BlockBreakEvent;

public class Oxygenate extends BlockBreakEventEnchant {

    public Oxygenate() {
        super("Oxygenate", EnchantTier.SIMPLE, 1, EnchantType.TOOL, "Refills oxygen levels when breaking blocks under water.");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        event.getPlayer().setRemainingAir(event.getPlayer().getMaximumAir());
    }
}
