package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.utils.cache.Cache;

public class DivineCache extends Cache {

    private long lastProcTime;

    public DivineCache() {
        super("divine_proc_time");
    }

    public boolean isDivineActive() {
        return lastProcTime + 3000L > System.currentTimeMillis();
    }

    public void updateLastProcTime() {
        this.lastProcTime = System.currentTimeMillis();
    }
}
