package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class CrystallineBand extends Skin {

    public CrystallineBand() {
        super("Crystalline Band", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to Icy Wrath Ability", "&cObsidian Guardians X", "&c20% Chance to negate Blackout proc");
    }
}
