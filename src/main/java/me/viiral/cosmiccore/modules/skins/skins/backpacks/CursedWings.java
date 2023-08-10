package me.viiral.cosmiccore.modules.skins.skins.backpacks;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class CursedWings extends Skin {


    public CursedWings() {
        super("Cursed Wings", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c-75% Silence Duration", "&cBlocks all enemy Rocket Escape", "&c-3% Incoming Damage");
    }
}
