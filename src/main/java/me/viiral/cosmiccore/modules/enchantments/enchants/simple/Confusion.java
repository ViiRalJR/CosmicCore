package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Confusion extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.006;

    public Confusion() {
        super("Insomnia", EnchantTier.SIMPLE, 7, EnchantType.AXE, "Chance to deal nausea to your victim. ");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 0, true));
        }
    }
}