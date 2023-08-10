package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class InfinityBelt extends Skin {

    public InfinityBelt() {
        super("Infinity Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkAqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cEquipped armor will regain to 10% durability", "&cinstead of breaking (10m cooldown)", "&cImmune to Soul Trap", "&cImmune to (Obsidian) Guardians", "&cNegates enemy (Reinforced) Tank");
    }
}
