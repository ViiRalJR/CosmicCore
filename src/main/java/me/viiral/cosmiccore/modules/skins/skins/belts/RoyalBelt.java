package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class RoyalBelt extends Skin {

    public RoyalBelt() {
        super("Royal Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c25% chance to not proc Pet Cooldown");
    }
}
