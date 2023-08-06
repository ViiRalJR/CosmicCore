package me.viiral.cosmiccore.utils;

import me.viiral.cosmiccore.CosmicCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.yaml.snakeyaml.Yaml;

import java.text.DecimalFormat;

public class DamageHandler {

    private final CosmicCore plugin;
    private final WorldGuardUtils worldGuardUtils;
    private final FileConfiguration config;
    private final DecimalFormat df;
    private final boolean debug;

    public DamageHandler(CosmicCore plugin) {
        this.plugin = plugin;
        this.worldGuardUtils = new WorldGuardUtils();
        this.config = plugin.getConfig();
        this.df = new DecimalFormat("#.##");
        this.debug = this.config.getBoolean("debug-enabled");
    }

    public void cancelDamage(EntityDamageEvent event, String reason) {
        event.setDamage(0);
        if (debug && event.getEntity() instanceof Player) {
            this.sendDebugMessage(event.getEntity(), "&c&lDMG Canceled &7(Reason: " + reason + "&7)");
        }
    }

    public void setDamage(EntityDamageEvent event, double amount, String reason) {
        event.setDamage(amount);
        if (debug && event.getEntity() instanceof Player) {
            this.sendDebugMessage(event.getEntity(), "&c&lDMG &7(&b=" + this.formatDamageAmount(amount) + " DMG&7) &7(Reason: " + reason + "&7)");
        }
    }

    public void damage(Player victim, double amount, String reason) {
        victim.damage(amount);

        if (debug) {
            this.sendDebugMessage(victim, "&c&l " + this.formatDamageAmount(amount) + " DMG &7(Reason: " + reason + "&7)");
        }
    }

    public void increaseDamage(double amountInPercentage, EntityDamageByEntityEvent event, String damageCause) {
        double increaseAmount = this.getCorrectIncreasePercentage(amountInPercentage);
        this.increaseDamage(amountInPercentage, event, damageCause, increaseAmount);
    }


    public void reduceDamage(double amountInPercentage, EntityDamageEvent event, String damageCause) {
        double decreaseAmount = this.getCorrectDecreasePercentage(amountInPercentage);
        this.reduceDamage(amountInPercentage, event, damageCause, decreaseAmount);
    }

    public void removeHealth(LivingEntity to, double amount, String damageCause) {
        if (!this.worldGuardUtils.canPvPInRegion(to.getLocation())) return;

        double getNewHealth = Math.max(to.getHealth() - amount, 0);

        if (debug && to instanceof Player) {
            this.sendDebugMessage(to, "&c&l- " + this.formatDamageAmount(amount) + " Health &7(Damage Cause: " + damageCause + "&7)");
        }

        to.setHealth(getNewHealth);
    }

    public void healEntity(LivingEntity entity, double amount, String healReason) {
        if (entity == null) return;
        if (entity.isDead()) return;

        double finalHealth = Math.min(entity.getHealth() + amount, entity.getMaxHealth());

        entity.setHealth(finalHealth);

        if (debug) {
            this.sendDebugMessage(entity, "&a&l+ " + this.formatDamageAmount(amount) + " HP &7(Heal Reason: " + healReason + "&7)");
        }

    }

    private void increaseDamage(double amountInPercentage, EntityDamageByEntityEvent event, String damageCause, double increaseAmount) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;

        Player victim = ((Player) event.getEntity());
        Player attacker = ((Player) event.getDamager());

        if (!this.worldGuardUtils.canPvPInRegion(attacker) || !this.worldGuardUtils.canPvPInRegion(victim)) return;
        double oldDamage = event.getDamage();
        double newDamage = oldDamage * increaseAmount;
        if (debug) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            double oldHealth = entity.getHealth();
            double healthDifference = this.getHealthDifference(entity.getHealth(), oldHealth);
            String color = healthDifference > 9.0D ? "&4&l" : "&c&l";
            this.sendDebugMessage(event.getDamager(), "&a&l\u2B06 Damage &7(&a+" + Math.round(amountInPercentage) + "%&7) &7(&c" + this.formatDamageAmount(oldDamage) + " &7-> &a" + this.formatDamageAmount(newDamage) + " &7[&f" + entity.getName() + ": " + color + "-" + (int) healthDifference + "HP&7]) &7(Damage Cause: &f" + damageCause + "&7)");
        }
        event.setDamage(newDamage);
    }

    private void reduceDamage(double amountInPercentage, EntityDamageEvent event, String damageCause, double decreaseAmount) {
        if (!this.worldGuardUtils.canPvPInRegion(event.getEntity().getLocation())) return;

        double oldDamage = event.getDamage();
        double newDamage = event.getDamage() * decreaseAmount;

        if (debug) {
            this.sendDebugMessage(event.getEntity(), "&c&l\u2B07 Damage &7(&c-" + Math.round(amountInPercentage) + "%&7) &7(&a" + this.formatDamageAmount(oldDamage) + " &7-> &c" + this.formatDamageAmount(newDamage) + "&7) (Damage Cause: &f" + damageCause + "&7)");
        }

        event.setDamage(newDamage);
    }

    private void sendDebugMessage(Entity entity, String message) {
        entity.sendMessage(CC.translate(message));
        Bukkit.getConsoleSender().sendMessage(CC.translate(CC.Aqua + message));
    }

    private Double getCorrectIncreasePercentage(double value) {
        return value < 100.0D ? 0.01D * value + 1.0D : 0.01D * value;
    }

    private Double getCorrectDecreasePercentage(double value) {
        value = 0.01D * value;
        value = 1.0D - value;
        return value;
    }

    private Double getHealthDifference(double healthBeforeModified, double healthAfterModified) {
        double health = (double) Math.round(healthBeforeModified - healthAfterModified);
        health = Math.abs(health);
        return health;
    }

    private String formatDamageAmount(double damage) {
        return this.df.format(damage);
    }
}