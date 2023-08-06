package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gears extends ArmorEquipEventEnchant {

    public Gears() {
        super("Gears", EnchantTier.LEGENDARY, false, 3, EnchantType.BOOTS, "&fAdded speed when equipped.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        int effectLevel = level - 1;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, effectLevel), true);
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Speed")
                        .replace("{effect-level}", RomanNumeral.convertToRoman(effectLevel + 1))
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
                );
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        player.removePotionEffect(PotionEffectType.SPEED);
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Speed")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }
}
