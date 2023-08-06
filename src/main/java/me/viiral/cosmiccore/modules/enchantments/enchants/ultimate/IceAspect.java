package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SlownessEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IceAspect extends WeaponDamageEventEnchant implements SlownessEnchant {

    @ConfigValue
    private double procChance = 0.003;
    @ConfigValue
    private int slownessAmplifier = 1;

    public IceAspect() {
        super("Ice Aspect", EnchantTier.ELITE, 3, EnchantType.SWORD, "A chance of causing the slowness", "effect to your enemy.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, slownessAmplifier, true));
        }
    }
}