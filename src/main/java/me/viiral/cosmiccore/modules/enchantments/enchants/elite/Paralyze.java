package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

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

public class Paralyze extends WeaponDamageEventEnchant implements SlownessEnchant {

    @ConfigValue
    private double procChance = 0.05;

    public Paralyze() {
        super("Paralyze", EnchantTier.ELITE, 4, EnchantType.SWORD, "Gives lightning effect and a chance for slowness and slow swinging.", "Also inflicts direct damage on proc.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player playerVictim)) return;

        if (Math.random() < procChance) {
            int level = enchantedItemBuilder.getEnchantmentLevel(this);

            playerVictim.getWorld().strikeLightningEffect(playerVictim.getLocation());

            this.addPotionEffect(playerVictim, PotionEffectType.SLOW, 100, level > 2 ? 1 : 0);

            if (level == 4) {
                this.addPotionEffect(playerVictim, PotionEffectType.SLOW, 100, 1);
            }
        }
    }
}
