package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends BlockBreakEventEnchant {

    public Haste() {
        super("Haste", EnchantTier.SIMPLE, 3, EnchantType.PICKAXE, "allows you to swing your tools faster.");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        this.addPotionEffect(event.getPlayer(), PotionEffectType.FAST_DIGGING, 100, enchantedItemBuilder.getEnchantmentLevel(this) - 1);
    }
}
