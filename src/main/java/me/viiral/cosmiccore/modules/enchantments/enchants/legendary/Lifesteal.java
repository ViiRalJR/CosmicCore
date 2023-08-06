package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Lifesteal extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.01;
    @ConfigValue
    private double healAmount = 5.0;
    @ConfigValue
    private int cooldown = 15;

    public Lifesteal() {
        super("Lifesteal", EnchantTier.LEGENDARY, 5, EnchantType.SWORD, "Has a chance of regaining health when", "attacking a player");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (super.isOnCooldown(attacker)) return;

//        Player user = (Player) victim;
//        if (EliteAPI.hasMask(user.getInventory().getHelmet()) && user.getInventory().getHelmet() != null && EliteAPI.hasMaskEffect(user.getInventory().getHelmet(), "Necromancer")) return;

        if (Math.random() < this.procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            super.getDamageHandler().healEntity(attacker, this.healAmount, this.getName());
            super.registerCooldown(attacker, this.cooldown);
        }
    }
}
