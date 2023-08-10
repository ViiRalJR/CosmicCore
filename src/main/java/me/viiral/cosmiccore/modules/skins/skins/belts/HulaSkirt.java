package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class HulaSkirt extends Skin {

    public HulaSkirt() {
        super("Hula Skirt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to Lifesteal", "&c+30% more chance to get loot in /warp ruins");
    }
}
