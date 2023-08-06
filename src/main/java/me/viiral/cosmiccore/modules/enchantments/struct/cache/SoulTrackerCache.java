package me.viiral.cosmiccore.modules.enchantments.struct.cache;

import me.viiral.cosmiccore.utils.cache.Cache;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SoulTrackerCache extends Cache {

    private final Map<UUID, Long> soulHarvestCooldowns;
    private static final long COOLDOWN = 600000;

    public SoulTrackerCache() {
        super("soul-tracker");
        this.soulHarvestCooldowns = new HashMap<>();
    }

    public boolean isOnHarvestCooldown(Player killedPlayer) {
        if (!this.soulHarvestCooldowns.containsKey(killedPlayer.getUniqueId())) return false;
        return this.soulHarvestCooldowns.get(killedPlayer.getUniqueId()) + 600000 > System.currentTimeMillis();
    }

    public long getSecondsLeftOnHarvestCooldown(Player killedPlayer) {
        long claimedTimeStamp = this.soulHarvestCooldowns.get(killedPlayer.getUniqueId());
        long currentTimeStamp = System.currentTimeMillis();
        long difference = currentTimeStamp - claimedTimeStamp;
        return difference >= COOLDOWN ? 0L : TimeUnit.MILLISECONDS.toSeconds(COOLDOWN - difference);
    }

    public void addHarvestCooldown(Player killedPlayer) {
        this.soulHarvestCooldowns.put(killedPlayer.getUniqueId(), System.currentTimeMillis());
    }
}
