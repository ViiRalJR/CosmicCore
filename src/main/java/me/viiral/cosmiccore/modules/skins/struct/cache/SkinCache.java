package me.viiral.cosmiccore.modules.skins.struct.cache;

import lombok.Getter;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.utils.cache.Cache;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SkinCache extends Cache {

    private final List<Skin> skins;
    private final Player player;

    public SkinCache(Player player) {
        super("skins");
        this.player = player;
        this.skins = new ArrayList<>();
    }

    public void addSkin(Skin skin) {
        if (!this.skins.contains(skin)) {
            this.skins.add(skin);
        }
    }

    public void removeSkin(Skin skin) {
        this.skins.remove(skin);
    }

    public boolean hasSkin() {
        return this.skins != null && this.skins.size() > 0;
    }

    public void clear() {
        this.skins.clear();
    }
}
