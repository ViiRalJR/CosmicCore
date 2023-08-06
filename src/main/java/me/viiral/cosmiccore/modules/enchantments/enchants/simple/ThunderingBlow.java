package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ThunderingBlow extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.06;

    public ThunderingBlow() {
        super("Thundering Blow", EnchantTier.SIMPLE, 3, EnchantType.SWORD, "&fChance to smite ur victim", "during combat.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance) {
            victim.getWorld().strikeLightningEffect(victim.getLocation());
            victim.setHealth(victim.getHealth() - enchantedItemBuilder.getEnchantmentLevel(this));
        }
    }
}
