package me.viiral.cosmiccore.modules.enchantments.struct.cache;

import me.viiral.cosmiccore.utils.cache.cooldown.CurrentCooldown;

import static me.viiral.cosmiccore.modules.NbtTags.WEAK_SHOT_CACHE_ID;

public class WeakShotCooldown extends CurrentCooldown {

    public WeakShotCooldown() {
        super(WEAK_SHOT_CACHE_ID, 50);
    }
}
