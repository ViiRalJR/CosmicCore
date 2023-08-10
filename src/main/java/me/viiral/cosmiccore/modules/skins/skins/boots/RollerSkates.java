package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class RollerSkates extends Skin {

    public RollerSkates() {
        super("Roller Skates", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cAuto Bless", "&cImmune to Skrrt Pet", "&c+10% Monopoly Chance");
    }
}
