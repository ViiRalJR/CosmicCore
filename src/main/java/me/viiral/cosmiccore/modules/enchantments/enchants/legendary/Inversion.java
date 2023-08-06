package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponIncomingDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Inversion extends WeaponIncomingDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.035;

    public Inversion() {
        super("Inversion", EnchantTier.LEGENDARY, 4, EnchantType.SWORD, "Damage dealt to you has a % chance to be blocked", "and heal you for 1-3 HP instead.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            super.getDamageHandler().cancelDamage(event, this.getName());
            super.getDamageHandler().healEntity(victim, ThreadLocalRandom.current().nextInt(1, 4), this.getName());
        }
    }
}
