package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;


import lombok.Getter;
import me.viiral.cosmiccore.utils.cache.Cache;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RageStacksCache extends Cache {

    @Getter
    private long lastRageStackTime;
    private int rageStack;
    private final int maxRageStack;
    private UUID victim;

    public RageStacksCache() {
        super("rage_stacks");
        this.rageStack = 1;
        this.maxRageStack = 10;
        this.updateLastRageStackTime();
        this.victim = null;
    }

    public void updateVictim(Player victim) {
        this.victim = victim.getUniqueId();
    }

    public boolean isSameVictim(Player victim) {
        if (this.victim == null) return false;
        return victim.getUniqueId().equals(this.victim);
    }

    public void updateLastRageStackTime() {
        this.lastRageStackTime = System.currentTimeMillis();
    }

    public void incrementRageStack() {
        this.rageStack++;
    }

    public void resetRageStack() {
        this.rageStack = 0;
    }

    public int getRageStack() {
        return Math.min(this.maxRageStack, rageStack);
    }
}
