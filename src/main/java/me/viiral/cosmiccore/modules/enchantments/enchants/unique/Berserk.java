package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

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

public class Berserk extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.03;

    public Berserk() {
        super("Berserk", EnchantTier.UNIQUE, 5, EnchantType.AXE, "A chance of strength and mining fatigue.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            if (!attacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, enchantedItemBuilder.getEnchantmentLevel(this) * 20, 1));
        }
    }
}
