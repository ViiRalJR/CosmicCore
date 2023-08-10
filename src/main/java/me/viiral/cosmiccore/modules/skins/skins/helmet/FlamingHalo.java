package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class FlamingHalo extends Skin {

    public FlamingHalo() {
        super("Flaming Halo", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Yellow;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("+4% Outgoing Damage");
    }
}
