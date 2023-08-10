package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RocketBoots extends Skin {

    public RocketBoots() {
        super("Rocket Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to Sabotage", "&cGears IV");
    }
}
