package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JokerMask extends Mask {

    public JokerMask() {
        super("Joker", "");
    }


    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+4s Combat Tag on enemy players", "-3s Combat Tag on you");
    }
}
