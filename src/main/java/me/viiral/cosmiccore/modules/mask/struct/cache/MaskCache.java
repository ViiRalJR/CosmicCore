package me.viiral.cosmiccore.modules.mask.struct.cache;

import lombok.Getter;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.cache.Cache;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MaskCache extends Cache {

    private final List<Mask> masks;
    private final Player player;

    public MaskCache(Player player) {
        super("mask");
        this.player = player;
        this.masks = new ArrayList<>();
    }

    public void addMask(Mask type) {
        if (!this.masks.contains(type)) {
            this.masks.add(type);
        }
    }

    public void removeMask(Mask type) {
        if (this.masks.contains(type)) {
            this.masks.remove(type);
        }
    }

    public boolean hasMask() {
        return this.masks != null && this.masks.size() > 0;
    }

    public void clear() {
        this.masks.clear();
    }
}
