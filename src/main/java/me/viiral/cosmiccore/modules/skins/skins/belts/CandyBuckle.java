package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class CandyBuckle extends Skin {

    public CandyBuckle() {
        super("Candy Buckle", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Green;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cDisable enemy's (Alien) Implants for 10s each hit", "&c+1% Outgoing Damage for each Food Level", "&cunder 20 that the enemy is missing (max +10%)", "&c10% chance for Speed IV for 10s when damaged");
    }
}
