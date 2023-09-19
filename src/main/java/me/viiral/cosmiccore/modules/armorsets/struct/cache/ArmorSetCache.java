package me.viiral.cosmiccore.modules.armorsets.struct.cache;

import lombok.Getter;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.utils.cache.Cache;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class ArmorSetCache extends Cache {

    private final HashMap<ArmorSet, Integer> armorSets;
    private ArmorSet currentArmorSet;
    private final Player player;

    public ArmorSetCache(Player player) {
        super("armor_set");
        this.player = player;
        this.armorSets = new HashMap<>();
        this.currentArmorSet = null;
    }

    public void addArmorSet(ArmorSet type) {
        if (!this.armorSets.containsKey(type)) {
            this.armorSets.put(type, 1);
        } else {
            this.armorSets.put(type, this.armorSets.get(type) + 1);
            if (this.armorSets.containsKey(type) && this.armorSets.get(type) == 4) {
                this.currentArmorSet = type;
                this.player.sendMessage(type.getEquipMessage().toArray(new String[]{}));
                this.player.playSound(this.player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
            }
        }
    }

    public void removeArmorSet(ArmorSet type) {
        if (this.armorSets.containsKey(type)) {
            int amount = this.armorSets.get(type);
            if (amount - 1 == 0) {
                this.armorSets.remove(type);
            } else {
                this.armorSets.put(type, this.armorSets.get(type) - 1);
            }
            if (this.armorSets.containsKey(type) && this.armorSets.get(type) != 4) {
                this.currentArmorSet = null;
            }
        }
    }

    public void clear() {
        this.currentArmorSet = null;
        this.armorSets.clear();
    }

    public boolean hasFullArmorSet() {
        return this.currentArmorSet != null;
    }
}
