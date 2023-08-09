package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class PilgrimMask extends Mask {

    public PilgrimMask() {
        super("Pilgrim", "");
    }


    @Override
    public String getColor() {
        return CC.Yellow;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c+25% XP/Drops");
    }
}
