package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class NeonEmotions extends Skin {

    public NeonEmotions() {
        super("Neon Emotions", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&c+6% Outgoing Damage",
                "&c-2% Incoming Damage",
                "&cImmune to Slowness Potion Effect"
        );
    }
}
