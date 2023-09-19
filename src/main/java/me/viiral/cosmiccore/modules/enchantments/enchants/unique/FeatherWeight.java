package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FeatherWeight extends WeaponDamageEventEnchant {


    public FeatherWeight() {
        super("Feather Weight", EnchantTier.UNIQUE,  3, EnchantType.SWORD, "&fHas a chance to give a burst of haste");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (attacker.hasPotionEffect(PotionEffectType.FAST_DIGGING)) return;

        if (Math.random() <= enchantedItemBuilder.getEnchantmentLevel(this) * 0.2) {
            int level = enchantedItemBuilder.getEnchantmentLevel(this);
            this.addPotionEffect(attacker, PotionEffectType.FAST_DIGGING, level * 30, level -1);
        }
    }
}
