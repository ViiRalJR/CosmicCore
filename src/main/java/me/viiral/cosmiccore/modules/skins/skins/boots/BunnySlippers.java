package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class BunnySlippers extends Skin {

    public BunnySlippers() {
        super("Bunny Slippers", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cWeb/Lava Walker", "&c2% Dodge");
    }
}
