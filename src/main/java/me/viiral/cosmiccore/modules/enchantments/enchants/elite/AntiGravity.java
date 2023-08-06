package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AntiGravity extends ArmorEquipEventEnchant {

    public AntiGravity() {
        super("Anti Gravity", EnchantTier.ELITE, false,3, EnchantType.BOOTS, "&fAdded extreme jump boost when equipped.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, level + 2), true);
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Jump Boost")
                        .replace("{effect-level}", RomanNumeral.convertToRoman(level))
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        player.removePotionEffect(PotionEffectType.JUMP);
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Jump Boost")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }
}
