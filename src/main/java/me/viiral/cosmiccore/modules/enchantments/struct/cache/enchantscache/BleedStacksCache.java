package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.utils.cache.Cache;
import lombok.Getter;

public class BleedStacksCache extends Cache {

    @Getter
    private long lastBleedStackTime;
    private int bleedStack;
    private final int maxBleedStack;

    public BleedStacksCache() {
        super("bleed_stacks");
        this.bleedStack = 1;
        this.maxBleedStack = 5;
        this.updateLastBleedStackTime();
    }

    public void updateLastBleedStackTime() {
        this.lastBleedStackTime = System.currentTimeMillis();
    }

    public void incrementBleedStack() {
        this.bleedStack++;
    }

    public void resetBleedStack() {
        this.bleedStack = 0;
    }

    public int getBleedStack() {
        return Math.min(this.maxBleedStack, bleedStack);
    }
}
