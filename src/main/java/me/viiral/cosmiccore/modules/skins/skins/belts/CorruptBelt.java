package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class CorruptBelt extends Skin {

    public CorruptBelt() {
        super("Corrupt Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cGodly Overload V");
    }
}
