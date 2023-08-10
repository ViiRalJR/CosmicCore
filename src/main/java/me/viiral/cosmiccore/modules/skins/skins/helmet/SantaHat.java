package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class SantaHat extends Skin {

    public SantaHat() {
        super("Santa Hat", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&c+5% OUTGOING DMG",
                "&c-5% INCOMING DMG"
        );
    }
}
