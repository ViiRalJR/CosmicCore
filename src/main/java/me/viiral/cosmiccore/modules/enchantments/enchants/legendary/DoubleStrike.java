package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DoubleStrike extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.04;

    public DoubleStrike() {
        super("Double Strike", EnchantTier.LEGENDARY, 3, EnchantType.SWORD, "Has a chance to attack twice in one swing.", "All your enchants can re-proc on", "this second attack, and it occurs", "instantly");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> {
                if (victim == null || victim.isDead()) return;
                victim.damage(event.getFinalDamage(), attacker);
            }, 2L);

        }
    }
}
