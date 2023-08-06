package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.utils.cache.Cache;
import lombok.Getter;
import lombok.Setter;

public class DiminishCache extends Cache {

    @Getter @Setter
    private double lastDamage;

    public DiminishCache() {
        super("diminish_last_damage");
        this.lastDamage = -1;
    }
}
