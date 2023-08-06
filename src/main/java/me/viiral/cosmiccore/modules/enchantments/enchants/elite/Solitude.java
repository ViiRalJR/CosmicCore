package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

public class Solitude extends Enchantment {

    public Solitude() {
        super("Solitude", EnchantTier.ELITE, false, 3, EnchantType.WEAPON, "Increases chance and length of the Silence enchantment", "procing on enemy players by up to 3X. ");
    }
}
