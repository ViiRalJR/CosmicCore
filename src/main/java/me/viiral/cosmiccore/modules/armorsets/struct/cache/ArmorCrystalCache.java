package me.viiral.cosmiccore.modules.armorsets.struct.cache;

import lombok.Getter;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.utils.cache.Cache;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class ArmorCrystalCache extends Cache {

    private final HashMap<ArmorSet, Integer> crystals;
    private final Player player;

    public ArmorCrystalCache(Player player) {
        super("armor_crystal");
        this.player = player;
        this.crystals = new HashMap<>();
    }

    public void addArmorCrystal(ArmorSet type) {
        if (!this.crystals.containsKey(type)) {
            this.crystals.put(type, 1);
        } else {
            this.crystals.put(type, this.crystals.get(type) + 1);
        }
    }

    public void removeArmorCrystal(ArmorSet type) {
        if (this.crystals.containsKey(type)) {
            int amount = this.crystals.get(type);
            if (amount - 1 == 0) {
                this.crystals.remove(type);
            } else {
                this.crystals.put(type, this.crystals.get(type) - 1);
            }
        }
    }

    public boolean hasCrystals() {
        return this.crystals != null && this.crystals.keySet().size() > 0;
    }

    public void clear() {
        this.crystals.clear();
    }

}
