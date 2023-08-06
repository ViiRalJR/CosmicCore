package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorPlayerDeathEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Lifebloom extends ArmorPlayerDeathEventEnchant {

    @ConfigValue
    private int cooldown = 100;
    @ConfigValue
    private int range = 20;

    public Lifebloom() {
        super("Lifebloom", EnchantTier.UNIQUE, false, 5, EnchantType.LEGGINGS, "Completely heals allies and truces on your death.");
    }

    @Override
    public void runEntityDeathEvent(PlayerDeathEvent event, Player killer, Player victim, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
        victim.getNearbyEntities(range, range, range).forEach(entity -> {
            if (!(entity instanceof Player)) return;
            Player nearbyPlayer = (Player) entity;
            if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) return;
            if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;
            FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);
            if (fPlayer.getRelationTo(fPlayerNearby).isAtMost(Relation.NEUTRAL)) return;
            super.getDamageHandler().healEntity(nearbyPlayer, 100, this.getName());
        });
        super.registerCooldown(victim, cooldown);

    }
}
