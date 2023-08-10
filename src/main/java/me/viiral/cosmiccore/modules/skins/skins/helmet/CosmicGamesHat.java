package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CosmicGamesHat extends Skin {

    public CosmicGamesHat() {
        super("CosmicGames Hat", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("5% chance not to consume Slotbot Ticket on Spin");
    }
}
