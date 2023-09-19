package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class ReindeerAntlers extends Skin {

    // TODO: 19/09/2023 this

    public ReindeerAntlers() {
        super("Reindeer Antlers", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Gray;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("Chance to double active pet duration.");
    }
}
