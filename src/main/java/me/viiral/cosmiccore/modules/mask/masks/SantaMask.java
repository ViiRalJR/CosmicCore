package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SantaMask extends Mask implements EquippableMask {

    public SantaMask() {
        super("Santa", "");
    }


    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c+2 Max Hearts");
    }

    @Override
    public void onEquip(Player player) {

    }

    @Override
    public void onUnequip(Player player) {

    }
}
