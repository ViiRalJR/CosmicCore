package me.viiral.cosmiccore.modules.skins.skins;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class ViiRalSkin extends Skin {

    public ViiRalSkin() {
        super("ViiRal", "_HELMET");
    }
    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&dTest lore for the ViiRal skin");
    }
}
