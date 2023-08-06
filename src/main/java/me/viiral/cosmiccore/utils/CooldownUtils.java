package me.viiral.cosmiccore.utils;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.utils.cache.CachedPlayer;
import me.viiral.cosmiccore.utils.cache.cooldown.CurrentCooldown;
import org.bukkit.entity.Player;

public class CooldownUtils {

    public static void registerCooldown(Player player, CurrentCooldown cache) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        cachedPlayer.registerCache(cache);
    }

    public static String getFormattedCooldown(Player player, String cache) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        return ((CurrentCooldown)cachedPlayer.getCache(cache)).getFormattedTime();
    }

    public static boolean isCooldownActive(Player player, String cache) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        return cachedPlayer.hasCache(cache) && ((CurrentCooldown)cachedPlayer.getCache(cache)).isActive();
    }
}
