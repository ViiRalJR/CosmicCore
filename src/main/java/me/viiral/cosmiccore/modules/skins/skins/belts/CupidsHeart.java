package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class CupidsHeart extends Skin {

    public CupidsHeart() {
        super("Cupids Heart", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cBypass enemy (Paladin) Armored", "&cHoly Aegis VII", "&c+10% Outgoing Damage to Silenced enemies");
    }
}
