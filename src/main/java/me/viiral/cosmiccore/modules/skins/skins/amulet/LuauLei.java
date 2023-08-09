package me.viiral.cosmiccore.modules.skins.skins.amulet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.ArrayList;
import java.util.List;

public class LuauLei extends Skin {

    public LuauLei() {
        super("Luau Lei", SkinType.AMULET);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&c+25% Outgoing Soul Enchant damage");
        lore.add("&cChance to block Soul Enchant Damage Modifiers");
        return lore;
    }
}
