package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class ZuesMask extends Mask {

    public ZuesMask() {
        super("Zues", "");
    }


    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Natures Wrath");
    }
}
