package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BlockBreakEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends BlockBreakEventEnchant {

    public Haste() {
        super("Haste", EnchantTier.ELITE, 3, EnchantType.PICKAXE, "allows you to swing your tools faster.");
    }

    @Override
    public void runBlockBreakEvent(BlockBreakEvent event, EnchantedItemBuilder enchantedItemBuilder) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, enchantedItemBuilder.getEnchantmentLevel(this) - 1, true));
    }
}
