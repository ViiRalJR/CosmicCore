package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class LeviathanTrophy extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 this

    public LeviathanTrophy() {
        super("Leviathan Trophy", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.DarkAqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&cIncreased chance to drop Ancient Scrolls",
                "&cwhen killing Guardians in /ruins",
                "&cImmune to enemy Amulet of Corruption",
                "&c+1 Max Heart",
                "&c3% chance to silence enemy Soul Enchants",
                "&7 (7x7 blocks for 7s)"
        );
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_AMULET_OF_CORRUPTION);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_AMULET_OF_CORRUPTION);
    }
}
