package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class HolyMask extends Mask {

    public HolyMask() {
        super("Holy", "");
    }


    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Bleed slowness");
    }
}
