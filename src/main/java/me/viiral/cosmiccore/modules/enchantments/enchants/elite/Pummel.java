package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.events.SlownessEnchantProc;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pummel extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.08;
    @ConfigValue
    private double range = 7.0;
    @ConfigValue
    private int slownessLevel = 1;
    @ConfigValue
    private int cooldown = 30;

    public Pummel() {
        super("Pummel", EnchantTier.ELITE, 3, EnchantType.AXE, "Chance to slow nearby enemy players", "for a short period of time.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (super.isOnCooldown(attacker)) return;
        if (Math.random() < procChance) {
            SlownessEnchantProc slownessEnchantProcEvent = new SlownessEnchantProc(attacker, victim, this, enchantedItemBuilder.getEnchantmentLevel(this));
            Bukkit.getPluginManager().callEvent(slownessEnchantProcEvent);

            if (event.isCancelled()) return;

            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(attacker);
            int level = enchantedItemBuilder.getEnchantmentLevel(this);
            attacker.getNearbyEntities(range, range, range).forEach(entity -> {
                if (!(entity instanceof Player nearbyPlayer)) return;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) return;
                this.addPotionEffect(nearbyPlayer, PotionEffectType.SLOW, (level + 2) * level, slownessLevel);
            });
            super.registerCooldown(attacker, cooldown);
        }
    }
}
