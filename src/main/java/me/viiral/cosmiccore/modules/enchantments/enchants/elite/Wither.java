package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wither extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.008;
    @ConfigValue
    private int duration = 3;
    @ConfigValue
    private int witherAmplifier = 0;

    public Wither() {
        super("Wither", EnchantTier.ELITE, false, 5, EnchantType.ARMOR, "A chance to give the wither effect.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (Math.random() < procChance * enchantInfo.getLevel()) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, duration * 20, witherAmplifier, true));
        }
    }
}
