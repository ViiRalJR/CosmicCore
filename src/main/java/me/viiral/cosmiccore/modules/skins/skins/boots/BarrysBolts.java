package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class BarrysBolts extends Skin {

    public BarrysBolts() {
        super("Barrys Bolts", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cImmune to Slowness Potion Effect", "&c-15% damage from enemy (Bloody) Deep Wounds");
    }
}
