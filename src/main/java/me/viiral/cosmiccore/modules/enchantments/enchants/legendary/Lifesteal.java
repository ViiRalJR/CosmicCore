package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;

import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.modules.user.User;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
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

        User user = CosmicCore.getInstance().getUserManager().getUsers().get(victim.getUniqueId());

        if (user != null && user.types.contains(EffectType.IMMUNE_TO_LIFESTEAL)) return;

        if (Math.random() < this.procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            super.getDamageHandler().healEntity(attacker, this.healAmount, this.getName());
            super.registerCooldown(attacker, this.cooldown);
        }
    }
}
