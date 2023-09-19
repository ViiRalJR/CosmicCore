package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SlownessEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Insomnia extends WeaponDamageEventEnchant implements SlownessEnchant {

    @ConfigValue
    private double procChance = 0.006;

    public Insomnia() {
        super("Insomnia", EnchantTier.SIMPLE, 7, EnchantType.SWORD, "Chance to proc slowness, mining fatigue", "and confusion against other", "players.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            this.addPotionEffect((Player) victim, PotionEffectType.SLOW_DIGGING, 80, 0);
            this.addPotionEffect((Player) victim, PotionEffectType.CONFUSION, 80, 0);
            this.addPotionEffect((Player) victim, PotionEffectType.SLOW, 80, 0);
        }
    }
}
