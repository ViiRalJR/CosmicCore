package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

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

public class Curse extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.4;
    @ConfigValue
    private int cooldown = 50;

    public Curse() {
        super("Curse", EnchantTier.UNIQUE, false, 5, EnchantType.CHESTPLATE, "Gives strength, slowness and resistance at low hp.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (victim.getHealth() > 6.0) return;
        if (super.isOnCooldown(victim)) return;
        if (Math.random() < procChance) {
            this.addPotionEffect(victim, PotionEffectType.INCREASE_DAMAGE, 30 * enchantInfo.getLevel(), 1);
            this.addPotionEffect(victim, PotionEffectType.DAMAGE_RESISTANCE, 30 * enchantInfo.getLevel(), 1);
            super.registerCooldown(victim, cooldown);
        }
    }
}
