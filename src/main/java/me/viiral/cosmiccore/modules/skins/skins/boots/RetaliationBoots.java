package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class RetaliationBoots extends Skin {

    public RetaliationBoots() {
        super("Retaliation Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+10% Outgoing Damage to Bosses", "&c+10% Incoming Damage");
    }
}
