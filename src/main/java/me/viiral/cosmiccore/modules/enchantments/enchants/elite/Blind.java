package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.BowEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.BlindingEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;


import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blind extends BowEventEnchant implements BlindingEnchant {

    @ConfigValue
    private double procChance = 0.03;

    public Blind() {
        super("Blind", EnchantTier.ELITE, 3, EnchantType.BOW, "Chance of causing blindness", "when attacking.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, Player victim, Player attacker, Arrow arrow, EnchantedItemBuilder enchantedItemBuilder) {
        if (victim.hasPotionEffect(PotionEffectType.BLINDNESS)) return;
        if (Math.random() <= procChance * enchantedItemBuilder.getEnchantmentLevel(this))
            victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, enchantedItemBuilder.getEnchantmentLevel(this) * 20, 0, true));
    }
}
