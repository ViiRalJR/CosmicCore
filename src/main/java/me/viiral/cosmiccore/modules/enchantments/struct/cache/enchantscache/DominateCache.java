package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.utils.cache.Cache;
import lombok.Getter;

@Getter
public class DominateCache extends Cache {

    private int level;
    private int duration;
    private long lastProcTime;
    private int damageReduction;

    public DominateCache() {
        super("dominate_proc_time");
        this.lastProcTime = -1;
        this.duration = 0;
    }

    public boolean isDominateActive() {
        return lastProcTime + duration * 1000L > System.currentTimeMillis();
    }

    public void procDominate(int level) {
        this.updateLastProcTime();
        this.updateLevel(level);
    }

    private void updateLastProcTime() {
        this.lastProcTime = System.currentTimeMillis();
    }

    private void updateLevel(int level) {
        this.level = level;
        this.updateDuration();
        this.updateDamageReduction();
    }
    private void updateDamageReduction() {
        this.damageReduction = this.level * 5;
    }

    private void updateDuration() {
        this.duration = this.level * 2;
    }
}
