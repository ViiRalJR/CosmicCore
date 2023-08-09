package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class ReindeerMask extends Mask {

    public ReindeerMask() {
        super("Reindeer", "");
    }


    @Override
    public String getColor() {
        return CC.Green;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cSPEED IV", "&cFlight regardless of rank");
    }
}
