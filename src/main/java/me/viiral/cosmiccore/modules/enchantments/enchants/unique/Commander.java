package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Commander extends ArmorIncomingPVPDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.04;
    @ConfigValue
    private int cooldown = 20;
    @ConfigValue
    private int range = 6;

    public Commander() {
        super("Commander", EnchantTier.UNIQUE, false, 5, EnchantType.ARMOR, "Nearby allies are given haste.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance * enchantInfo.getLevel()) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
            victim.getNearbyEntities(range, range, range).forEach(entity -> {
                if (!(entity instanceof Player)) return;
                Player nearbyPlayer = (Player) entity;
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
                if (fPlayer.getRelationTo(fPlayerNearby).isAtMost(Relation.NEUTRAL)) return;
                this.addPotionEffect(nearbyPlayer, PotionEffectType.FAST_DIGGING, 80, 1);
            });
            super.registerCooldown(victim, cooldown);
        }
    }
}
