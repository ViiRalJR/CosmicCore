package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Glowing extends ArmorEquipEventEnchant {

    public Glowing() {
        super("Glowing", EnchantTier.SIMPLE, false,1, EnchantType.HELMET, "Added Night Vision when equipped.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        this.addPotionEffect(player, PotionEffectType.NIGHT_VISION, 0,level - 1);

    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.NIGHT_VISION);
    }
}
