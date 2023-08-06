package me.viiral.cosmiccore.modules.enchantments.utils;

import me.viiral.cosmiccore.CosmicCore;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class PVPUtils {

    private PVPUtils() {

    }

    public static boolean canPVP(Player firstPlayer, LivingEntity secondPlayer) {
        if (!CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(firstPlayer.getLocation())) return false;
        if (!CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(secondPlayer.getLocation())) return false;
        if (!(secondPlayer instanceof Player)) return true;
        return !FactionUtils.cantPvPFaction(firstPlayer, (Player) secondPlayer);
    }

    public static boolean canPvPInRegion(Player player) {
        return CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(player.getLocation());
    }

    public static boolean canPvPInRegion(Location location) {
        return CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(location);
    }

    public static List<Player> getNearbyPlayers(Player player, int radius) {
        List<Player> nearbyPlayers = new ArrayList<>();
        nearbyPlayers.add(player);
        for (Entity nearbyEntity : player.getNearbyEntities(radius, radius, radius)) {
            if (nearbyEntity instanceof Player)
                nearbyPlayers.add((Player) nearbyEntity);
        }
        return nearbyPlayers;
    }

    public static List<Player> getNearbyPlayersExceptPlayer(Player player, int radius) {
        List<Player> nearbyPlayers = new ArrayList<>();
        for (Entity nearbyEntity : player.getNearbyEntities(radius, radius, radius)) {
            if (nearbyEntity instanceof Player)
                nearbyPlayers.add((Player) nearbyEntity);
        }
        return nearbyPlayers;
    }
}
