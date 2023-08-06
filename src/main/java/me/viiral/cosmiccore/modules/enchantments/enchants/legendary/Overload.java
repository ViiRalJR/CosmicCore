package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.RomanNumeral;import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Overload extends ArmorEquipEventEnchant implements Heroicable {

    public Overload() {
        super("Overload", EnchantTier.LEGENDARY, false, 3, EnchantType.ARMOR, "Increases the wearer's total health.");
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, level - 1), true);
        EnchantLanguage.EQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Health Boost")
                        .replace("{effect-level}", RomanNumeral.convertToRoman(level))
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        EnchantLanguage.UNEQUIP_POTION_ENCHANT.send(player,
                str -> str
                        .replace("{effect}", "Health Boost")
                        .replace("{enchant}", this.getName())
                        .replace("{enchant-level}", RomanNumeral.convertToRoman(level))
        );
    }

    @Override
    public Enchantment getHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Godly Overload");
    }
}
