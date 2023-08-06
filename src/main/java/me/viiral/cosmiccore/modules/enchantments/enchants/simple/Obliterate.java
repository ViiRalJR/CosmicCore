package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Obliterate extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.1;

    public Obliterate() {
        super("Obliterate", EnchantTier.SIMPLE, 5, EnchantType.AXE, "&fChance to give extreme knockback", "&fto your enemy.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            victim.setVelocity(attacker.getLocation().getDirection().multiply(6));
        }
    }
}
