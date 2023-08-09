package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class NecromancerMask extends Mask {

    public NecromancerMask() {
        super("Necromancer", "");
    }


    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Lifesteal");
    }
}
