package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.CosmicCore;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Vampire extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.01;
    @ConfigValue
    private int cooldown = 20;

    public Vampire() {
        super("Vampire", EnchantTier.ELITE, 3, EnchantType.SWORD, "A chance to heal you for up to 3hp", "a few seconds after you strike.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (super.isOnCooldown(attacker)) return;

        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> {
                super.getDamageHandler().healEntity(attacker, enchantedItemBuilder.getEnchantmentLevel(this), this.getName());
            }, 30L);
            attacker.playSound(attacker.getLocation(), Sound.FIREWORK_LAUNCH, 0.75f, 2.0f);
            super.registerCooldown(attacker, cooldown);
        }
    }
}
