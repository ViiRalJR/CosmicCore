package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class KnightBoots extends Skin {

    public KnightBoots() {
        super("Knight Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Gray;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to all but vanilla knockback");
    }
}
