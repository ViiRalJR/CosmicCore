package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ObsidianShield extends ArmorEquipEventEnchant {

    public ObsidianShield() {
        super("Obsidian Shield", EnchantTier.ULTIMATE, false, 1, EnchantType.ARMOR, "Gives permanent fire resistance.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0), true);
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Fire Resistance")
                        .replace("{effect-level}", "I")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Fire Resistance")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }
}
