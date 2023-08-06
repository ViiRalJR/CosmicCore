package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;


import lombok.Getter;
import me.viiral.cosmiccore.utils.cache.Cache;

@Getter
public class TeleblockCache extends Cache {

    private int level;
    private int duration;
    private long lastProcTime;

    public TeleblockCache() {
        super("teleblock");
        this.lastProcTime = -1;
        this.duration = 0;
    }

    public boolean isTeleblockActive() {
        return lastProcTime + duration > System.currentTimeMillis();
    }

    public void procTeleblock(int level) {
        this.updateLastProcTime();
        this.updateLevel(level);
    }

    private void updateLastProcTime() {
        this.lastProcTime = System.currentTimeMillis();
    }

    private void updateLevel(int level) {
        this.level = level;
        this.updateDuration();
    }

    private void updateDuration() {
        this.duration = this.level * 4;
    }
}
