package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Drunk extends ArmorEquipEventEnchant {

    public Drunk() {
        super("Drunk", EnchantTier.LEGENDARY, false, 4, EnchantType.HELMET, "Slowness and slow swing with", "buffed strength.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, Math.min(level - 1, 2)), true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, Math.min(level - 1, 2)), true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 2), true);
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Strength")
                        .replace("{effect-level}", RomanNumeral.convertToRoman(Math.min(level, 3)))
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Slowness")
                        .replace("{effect-level}", RomanNumeral.convertToRoman(Math.min(level, 3)))
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Mining Fatigue")
                        .replace("{effect-level}", RomanNumeral.convertToRoman(3))
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Strength")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Slowness")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Mining Fatigue")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }
}
