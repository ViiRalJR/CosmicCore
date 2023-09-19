package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.events.SlownessEnchantProc;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SlownessEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Trap extends WeaponDamageEventEnchant implements SlownessEnchant {

    @ConfigValue
    private double procChance = 0.003;
    @ConfigValue
    private int slownessAmplifier = 1;

    public Trap() {
        super("Trap", EnchantTier.ELITE, 3, EnchantType.SWORD, "A chance of causing the slowness", "effect to your enemy.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            SlownessEnchantProc slownessEnchantProcEvent = new SlownessEnchantProc(attacker, victim, this, enchantedItemBuilder.getEnchantmentLevel(this));
            Bukkit.getPluginManager().callEvent(slownessEnchantProcEvent);

            if (event.isCancelled()) return;

            this.addPotionEffect((Player) victim, PotionEffectType.SLOW, 60, slownessAmplifier);
        }
    }
}
