package me.viiral.cosmiccore.modules.skins.skins.backpacks;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class TeddyBackpack extends Skin {


    public TeddyBackpack() {
        super("Teddy Backpack", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to (Lethal) Sniper", "&cImmune to Hero Killer", "&c+2% Outgoing Damage");
    }
}
