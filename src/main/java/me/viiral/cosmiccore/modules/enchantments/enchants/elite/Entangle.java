package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BowEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Entangle extends BowEventEnchant {

    @ConfigValue
    private double procChance = 0.005;

    public Entangle() {
        super("Entangle", EnchantTier.ELITE, 4, EnchantType.BOW, "Chance to immobilize your target when", "they are hit.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, Player victim, Player attacker, Arrow arrow,  EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, Integer.MAX_VALUE, true));
        }
    }
}
