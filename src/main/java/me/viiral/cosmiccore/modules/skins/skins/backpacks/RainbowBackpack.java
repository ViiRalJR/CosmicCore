package me.viiral.cosmiccore.modules.skins.skins.backpacks;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class RainbowBackpack extends Skin {


    public RainbowBackpack() {
        super("Rainbow Backpack", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.Green;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c+1 Max Heart");
    }
}
