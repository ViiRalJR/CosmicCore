package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class AntiGravity extends ArmorEquipEventEnchant {

    public AntiGravity() {
        super("Anti Gravity", EnchantTier.ELITE, false,3, EnchantType.BOOTS, "&fAdded extreme jump boost when equipped.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        this.addPotionEffect(player, PotionEffectType.JUMP, 0,level + 2);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.JUMP);
    }
}
