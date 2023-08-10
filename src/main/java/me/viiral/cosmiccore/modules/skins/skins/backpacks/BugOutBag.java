package me.viiral.cosmiccore.modules.skins.skins.backpacks;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class BugOutBag extends Skin {


    public BugOutBag() {
        super("Bug Out Bag", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.Green;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c10% chance to keep each equipped item on death.");
    }
}
