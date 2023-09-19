package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Overload extends ArmorEquipEventEnchant implements Heroicable {

    public Overload() {
        super("Overload", EnchantTier.LEGENDARY, false, 3, EnchantType.ARMOR, "Increases the wearer's total health.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        this.addPotionEffect(player, PotionEffectType.HEALTH_BOOST, 0, level - 1);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.HEALTH_BOOST, level - 1);
    }

    @Override
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Godly Overload");
    }
}
