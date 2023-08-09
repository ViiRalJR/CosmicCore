package me.viiral.cosmiccore.modules.skins.skins.amulet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.ArrayList;
import java.util.List;

public class LostAmulet extends Skin {

    public LostAmulet() {
        super("Lost Amulet", SkinType.AMULET);
    }

    @Override
    public String getColor() {
        return CC.DarkAqua;
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&c50% Neutralize Negation");
        lore.add("&c-5% Incoming Damage");
        lore.add("&cIncreases outgoing damage from Soul Enchants by 1.1x");
        return lore;
    }
}
