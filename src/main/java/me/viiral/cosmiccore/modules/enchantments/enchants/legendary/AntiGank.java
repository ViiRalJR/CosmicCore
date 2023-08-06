package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.AntiGankCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiGank extends WeaponDamageEventEnchant {

    public AntiGank() {
        super("Anti Gank", EnchantTier.LEGENDARY, 4, EnchantType.AXE, "If more than (6-level) enemies hit you in a short period,", "your outgoing damage will be multiplied by up to 1.5x", "depending on the amount of enemies nearby.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;

        Player victimPlayer = ((Player) victim);
        AntiGankCache antiGankCache = CacheUtils.getAntiGankCache(attacker);

        antiGankCache.addPlayerToList(victimPlayer);

        if (!antiGankCache.isStillActive()) {
            antiGankCache.updateHitTime();
            antiGankCache.clearPlayers();
            return;
        }

        if (antiGankCache.getAmountOfPlayers() >= 6 - enchantedItemBuilder.getEnchantmentLevel(this)) {
            super.getDamageHandler().increaseDamage(50, event, this.getName());
        }
    }
}
