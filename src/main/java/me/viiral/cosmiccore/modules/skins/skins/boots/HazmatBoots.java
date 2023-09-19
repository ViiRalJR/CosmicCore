package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class HazmatBoots extends Skin {

    // TODO: 19/09/2023 Silence pet

    public HazmatBoots() {
        super("Hazmat Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cAuto Bless", "&cChance to activate Silence Pet", " &7(7x7 area for 12 seconds)");
    }
}
