package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class CandySortingBelt extends Skin {

    public CandySortingBelt() {
        super("Candy Sorting Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c5% Chance to negate Infinite Luck", "&cper Heroic Armor piece equipped", "&cBypasses Candy Collector's Sugar Rush");
    }
}
