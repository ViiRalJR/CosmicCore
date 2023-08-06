package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

public class DeepWounds extends Enchantment {

    public DeepWounds() {
        super("Deep Wounds", EnchantTier.UNIQUE, false, 3, EnchantType.WEAPON, "Increases the chance of you giving the bleed effect.");
    }
}
