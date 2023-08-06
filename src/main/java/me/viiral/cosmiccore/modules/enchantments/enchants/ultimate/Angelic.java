package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.tasks.AngelicTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitTask;

public class Angelic extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.004;
    @ConfigValue
    private int cooldown = 25;

    public Angelic() {
        super("Angelic", EnchantTier.ULTIMATE, true, 5, EnchantType.ARMOR, "Heals health over time whenever", "damaged by an enemy.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance * enchantInfo.getLevel()) {
            BukkitTask task = new AngelicTask(victim).runTaskTimer(CosmicCore.getInstance(), 20L, 20L);
            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), task::cancel, 100L);
            super.registerCooldown(victim, cooldown);
        }
    }
}
