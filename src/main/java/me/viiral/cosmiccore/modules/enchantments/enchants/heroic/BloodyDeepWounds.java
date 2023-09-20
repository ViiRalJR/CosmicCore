package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

public class BloodyDeepWounds extends Enchantment implements HeroicEnchant {

    public BloodyDeepWounds() {
        super("Bloody Deep Wounds", EnchantTier.HEROIC, false, 3, EnchantType.WEAPON, "Increases the chance of you giving the bleed effect.");
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Deep Wounds");
    }
}
