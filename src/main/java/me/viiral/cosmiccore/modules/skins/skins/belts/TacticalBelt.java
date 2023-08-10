package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TacticalBelt extends Skin {

    public TacticalBelt() {
        super("Tactical Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cIncreases pet ability duration by 1.5x");
    }
}
