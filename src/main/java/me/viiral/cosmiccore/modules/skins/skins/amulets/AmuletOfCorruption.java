package me.viiral.cosmiccore.modules.skins.skins.amulets;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.ArrayList;
import java.util.List;

public class AmuletOfCorruption extends Skin {

    public AmuletOfCorruption() {
        super("Amulet of Corruption", SkinType.AMULET);
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&cImmune to enemy armor crystals of same type on attached item.");
        return lore;
    }
}
