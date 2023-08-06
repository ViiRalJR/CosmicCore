package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Aquatic extends ArmorEquipEventEnchant {

    public Aquatic() {
        super("Aquatic", EnchantTier.SIMPLE, false,1, EnchantType.HELMET, "&fAdded water breathing when equipped.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, level - 1), true);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
    }
}
