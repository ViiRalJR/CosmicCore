package me.viiral.cosmiccore.utils.cache.cooldown;

import lombok.Getter;
import me.viiral.cosmiccore.utils.TimeUtils;
import me.viiral.cosmiccore.utils.cache.Cache;

@Getter
public class CurrentCooldown extends Cache {

    private final long started;
    private final long duration;

    public CurrentCooldown(String name, long duration) {
        super(name);
        this.started = System.currentTimeMillis();
        this.duration = duration * 1000;
    }

    public boolean isActive() {
        return this.started + this.duration > System.currentTimeMillis();
    }

    public String getFormattedTime() {
        return TimeUtils.formatSeconds((this.started + this.duration - System.currentTimeMillis())/1000);
    }
}
