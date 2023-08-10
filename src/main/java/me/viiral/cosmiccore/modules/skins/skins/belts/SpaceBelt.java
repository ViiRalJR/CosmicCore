package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class SpaceBelt extends Skin {

    public SpaceBelt() {
        super("Space Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cAuto Bless", "&c+5% Blackscroll Bonus", "&c+2% to not consume Book on apply");
    }
}
