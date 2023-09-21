package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class InfinityBelt extends Skin implements EquipableSkin {

    public InfinityBelt() {
        super("Infinity Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkAqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cEquipped armor will regain to 10% durability", "&cinstead of breaking (10m cooldown)", "&cImmune to Soul Trap", "&cImmune to (Obsidian) Guardians", "&cNegates enemy (Reinforced) Tank");
    }


    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_GUARDIANS);
        addEffect(player, EffectType.IMMUNE_TO_SOULTRAP);
        addEffect(player, EffectType.IMMUNE_TO_TANK);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_GUARDIANS);
        removeEffect(player, EffectType.IMMUNE_TO_SOULTRAP);
        removeEffect(player, EffectType.IMMUNE_TO_TANK);
    }
}
