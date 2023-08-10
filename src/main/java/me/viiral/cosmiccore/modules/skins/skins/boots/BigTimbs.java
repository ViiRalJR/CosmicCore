package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class BigTimbs extends Skin {

    public BigTimbs() {
        super("Big Timbs", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c35% chance on incoming damage to", "&cnegate enemy Rage stack multipliers", "&cImmune to Hero Killer", "&cImmune to enemy outgoing Heroic Pet abilities", "&cGears IV");
    }
}
