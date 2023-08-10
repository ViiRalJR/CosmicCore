package me.viiral.cosmiccore.modules.skins.skins.backpacks;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeepspaceBackpack extends Skin {


    public DeepspaceBackpack() {
        super("Deepspace Backpack", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+3% Outgoing Damage.", "&cImmune to Soul Trap");
    }
}
