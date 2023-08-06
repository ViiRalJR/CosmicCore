package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.SilenceCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Silenceable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Silence extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.05;
    @ConfigValue
    private int cooldown = 30;
    @ConfigValue
    private String messageAttacker = "&5&l* SILENCE &7[{duration}s]&5&l *";
    @ConfigValue
    private String messageVictim = "&5&l* SILENCED &7[{duration}s]&5&l *";

    public Silence() {
        super("Silence", EnchantTier.LEGENDARY, 4, EnchantType.SWORD, "Chance to stop activation of your enemies enchants.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        if (super.isOnCooldown(attacker)) return;

        

        if (Math.random() < this.procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            SilenceCache silenceCache = CacheUtils.getSilenceCache((Player) victim);
            if (silenceCache.isSilenced()) return;
            silenceCache.setSilenced(true);
            int silenceTime = enchantedItemBuilder.getEnchantmentLevel(this);

            if (enchantedItemBuilder.hasEnchantment("solitude")) {
                silenceTime += enchantedItemBuilder.getEnchantmentLevel("solitude");
            }

            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> silenceCache.setSilenced(false), silenceTime * 20L);
            super.registerCooldown(attacker, this.cooldown);
            int finalSilenceTime = silenceTime;
            super.sendMessage(attacker, this.messageAttacker, str -> str.replace("{duration}", String.valueOf(finalSilenceTime)));
            super.sendMessage(victim, this.messageVictim, str -> str.replace("{duration}", String.valueOf(finalSilenceTime)));
            victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_WITHER_HURT, 1.0f, 0.25f);
        }
    }

    @EventHandler
    public void runEnchantDamageProcEvent(EnchantProcOnEntityEvent event) {
        if (!(event.getEnchantment() instanceof Silenceable)) return;
        if (!(event.getVictim() instanceof Player)) return;

        if (CacheUtils.getSilenceCache((Player) event.getVictim()).isSilenced()) event.setCancelled(true);
    }
}
