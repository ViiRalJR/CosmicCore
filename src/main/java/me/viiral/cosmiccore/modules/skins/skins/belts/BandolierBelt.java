package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class BandolierBelt extends Skin {

    public BandolierBelt() {
        super("Bandolier Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkGray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cBypass enemy Witch Hat", "&cImmune to Deep Bleed Slowness", "&cLimits an enemy stackable enchant level by 50%");
    }
}
