package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Gears extends ArmorEquipEventEnchant {

    public Gears() {
        super("Gears", EnchantTier.LEGENDARY, false, 3, EnchantType.BOOTS, "&fAdded speed when equipped.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        int effectLevel = level - 1;
        this.addPotionEffect(player, PotionEffectType.SPEED, 0, effectLevel);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.SPEED, level - 1);
    }
}
