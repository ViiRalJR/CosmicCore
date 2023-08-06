package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Ravenous extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.05;

    public Ravenous() {
        super("Ravenous", EnchantTier.UNIQUE, 4, EnchantType.AXE, "A chance to regain hunger.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this) && attacker.getFoodLevel() < 20) {
            attacker.setFoodLevel(attacker.getFoodLevel() + 1);
        }
    }
}
