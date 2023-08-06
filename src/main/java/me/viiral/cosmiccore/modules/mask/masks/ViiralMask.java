package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class ViiralMask extends Mask {
    public ViiralMask() {
        super("viiral", "");
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("ViiRal test lore for mask");
    }
}
