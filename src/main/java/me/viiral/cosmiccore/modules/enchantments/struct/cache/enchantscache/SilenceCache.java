package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.utils.cache.Cache;
import lombok.Getter;
import lombok.Setter;

public class SilenceCache extends Cache {

    @Getter @Setter
    private boolean silenced;

    public SilenceCache() {
        super("silence_effect");
        this.silenced = false;
    }
}
