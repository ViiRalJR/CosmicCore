package me.viiral.cosmiccore.modules.skins.skins;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class DriftaySkin extends Skin {
    public DriftaySkin() {
        super("Driftay", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cTest lore for the Driftay skin");
    }
}
