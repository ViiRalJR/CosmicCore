package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class TeddySlippers extends Skin {

    public TeddySlippers() {
        super("Teddy Slippers", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cNerf enemy special Armor Set damage by 50%", "&c33% chance to reflect enemy (Titan) Trap", "&c-2% Incoming Damage", "&c+1% Outgoing Damage");
    }
}
