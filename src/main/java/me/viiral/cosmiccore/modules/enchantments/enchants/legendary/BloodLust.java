package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

public class BloodLust extends Enchantment {

    public BloodLust() {
        super("Blood Lust", EnchantTier.LEGENDARY, false, 6, EnchantType.CHESTPLATE, "A chance to heal you whenever an enemy", "player within 7x7 blocks is damaged", "by the Bleed enchantment. ");
    }
}
