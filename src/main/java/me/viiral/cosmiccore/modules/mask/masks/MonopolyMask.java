package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MonopolyMask extends Mask {

    public MonopolyMask() {
        super("Monopoly", "");
    }


    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c33% Holy White Scroll negation", "&c-5% ENEMY DMG");
    }
}
