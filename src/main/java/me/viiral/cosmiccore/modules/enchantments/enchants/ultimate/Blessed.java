package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.player.PotionEffectUtils;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Blessed extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.2;
    @ConfigValue
    private String message = "&e&l** BLESSED **";

    public Blessed() {
        super("Blessed", EnchantTier.ULTIMATE, 4, EnchantType.AXE, "Has a chance of removing debuffs.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < (this.procChance * enchantedItemBuilder.getEnchantmentLevel(this))) {
            super.sendMessage(attacker, this.message);
            attacker.playSound(attacker.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1.2f, 2.0f);
            PotionEffectUtils.bless(attacker);
        }
    }
}
