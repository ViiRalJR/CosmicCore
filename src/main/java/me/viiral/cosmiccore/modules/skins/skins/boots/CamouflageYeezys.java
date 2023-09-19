package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class CamouflageYeezys extends Skin {

    // TODO: 19/09/2023 this

    public CamouflageYeezys() {
        super("Camouflage Yeezys", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c40% chance to negate soul trap", "&cDemonic Gateway V");
    }
}
