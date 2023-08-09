package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SantaMask extends Mask {

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
}
