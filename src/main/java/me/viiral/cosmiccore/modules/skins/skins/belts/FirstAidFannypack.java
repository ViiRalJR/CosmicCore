package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class FirstAidFannypack extends Skin {

    public FirstAidFannypack() {
        super("First Aid Fannypack", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c50% chance to negate Blackout proc");
    }
}
