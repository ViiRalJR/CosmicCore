package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class ChainBelt extends Skin {

    public ChainBelt() {
        super("Chain Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Gray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cAuto Bless", "&c200% damage to Citadel Siege blocks", "&c+2.5% Outgoing Damage");
    }
}
