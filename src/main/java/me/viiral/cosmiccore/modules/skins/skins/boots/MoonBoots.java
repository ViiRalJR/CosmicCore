package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoonBoots extends Skin {

    // TODO: 19/09/2023 Increase armor set bonus and crystal

    public MoonBoots() {
        super("Moon Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cIncreases armor set bonus/crystal damage by 1.2x");
    }
}
