package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class BloodstainedGaloshes extends Skin {

    public BloodstainedGaloshes() {
        super("Bloodstained Galoshes", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cImmune to World Destroyer Cage", "&c25% chance to reflect cage back at the user", "&cImmune to Slowness/Freezes", "Increases Execute HP threshold to proc by 1.3x");
    }
}
