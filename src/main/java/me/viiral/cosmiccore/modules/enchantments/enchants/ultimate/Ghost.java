package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

public class Ghost extends Enchantment {

    public Ghost() {
        super("Ghost", EnchantTier.ULTIMATE, false, 1, EnchantType.ARMOR, "&fInvisible to /near lookups");
    }
}
