package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class TerminatorMask extends Mask {

    public TerminatorMask() {
        super("Terminator", "");
    }


    @Override
    public String getColor() {
        return CC.DarkRed;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cNegate 50% Shockwave, Ragdoll, Rocket Escape", "&cSpeed IV");
    }
}
