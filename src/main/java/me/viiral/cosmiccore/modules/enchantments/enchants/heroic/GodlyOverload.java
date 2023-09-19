package me.viiral.cosmiccore.modules.enchantments.enchants.heroic;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class GodlyOverload extends ArmorEquipEventEnchant implements HeroicEnchant {

    public GodlyOverload() {
        super("Godly Overload", EnchantTier.HEROIC, false, 3, EnchantType.ARMOR, "Increases the wearer's total health.");
        setHeroic();
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        this.addPotionEffect(player, PotionEffectType.HEALTH_BOOST, 0, (level * 2) - 1);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {
        this.removePotionEffect(player, PotionEffectType.HEALTH_BOOST);
    }

    @Override
    public Enchantment getNonHeroicEnchant() {
        return CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromName("Overload");
    }
}
