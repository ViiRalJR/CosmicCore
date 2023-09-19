package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;

import java.util.Arrays;
import java.util.List;

public class WitchHat extends Skin {

    // TODO: 19/09/2023 this

    public WitchHat() {
        super("Witch Hat", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Yellow;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&c-25% Silence duration",
                "&cImmune to offensive Titan Attributes",
                "&7(Atlas, Ouranos and Prometheus",
                "&cImmune to Trap",
                "&cImmune to Bloodhound & Banshee Pet",
                "&c10% chance to deflect incoming damage",
                "&conto nearby enemy",
                "&c2% chance to enable a LVL 10",
                "&cBanshee Pet effect when damaged"
        );
    }
}
