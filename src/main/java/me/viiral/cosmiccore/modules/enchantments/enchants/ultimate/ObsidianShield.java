package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class ObsidianShield extends ArmorEquipEventEnchant {

    public ObsidianShield() {
        super("Obsidian Shield", EnchantTier.ULTIMATE, false, 1, EnchantType.ARMOR, "Gives permanent fire resistance.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        this.addPotionEffect(player, PotionEffectType.FIRE_RESISTANCE, 0, 0);

    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.FIRE_RESISTANCE);
    }
}
