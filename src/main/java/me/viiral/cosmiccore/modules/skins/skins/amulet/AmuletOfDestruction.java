package me.viiral.cosmiccore.modules.skins.skins.amulet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.ArrayList;
import java.util.List;

public class AmuletOfDestruction extends Skin {

    public AmuletOfDestruction() {
        super("Amulet of Destruction", SkinType.AMULET);
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&c+7.5% Outgoing Damage");
        return lore;
    }
}
