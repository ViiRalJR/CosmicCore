package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GlitchMask extends Mask {

    public GlitchMask() {
        super("Glitch", "");
    }


    @Override
    public String getColor() {
        return CC.White;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Teleblock");
    }
}
