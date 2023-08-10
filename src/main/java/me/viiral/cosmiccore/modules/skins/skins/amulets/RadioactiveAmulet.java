package me.viiral.cosmiccore.modules.skins.skins.amulets;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.ArrayList;
import java.util.List;

public class RadioactiveAmulet extends Skin {

    public RadioactiveAmulet() {
        super("Radioactive Amulet", SkinType.AMULET);
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&c15% chance for double loot from Mobs in /warp end");
        lore.add("&c-50% Incoming PvE Damage in /warp end");
        return lore;
    }
}
