package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Drunk extends ArmorEquipEventEnchant {

    public Drunk() {
        super("Drunk", EnchantTier.LEGENDARY, false, 4, EnchantType.HELMET, "Slowness and slow swing with", "buffed strength.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        this.addPotionEffect(player, PotionEffectType.INCREASE_DAMAGE, 0, Math.min(level - 1, 2));
        this.addPotionEffect(player, PotionEffectType.SLOW, 0, Math.min(level - 1, 2));
        this.addPotionEffect(player, PotionEffectType.SLOW_DIGGING, 0, 2);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.INCREASE_DAMAGE);
        this.removePotionEffect(player, PotionEffectType.SLOW);
        this.removePotionEffect(player, PotionEffectType.SLOW_DIGGING);

    }
}
