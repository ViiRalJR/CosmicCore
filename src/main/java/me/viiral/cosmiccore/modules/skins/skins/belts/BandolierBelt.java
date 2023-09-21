package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BandolierBelt extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 this

    public BandolierBelt() {
        super("Bandolier Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkGray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cBypass enemy Witch Hat", "&cImmune to Deep Bleed Slowness");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_DEEPBLEED_SLOWNESS);
        addEffect(player, EffectType.IMMUNE_TO_WITCHHAT);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_DEEPBLEED_SLOWNESS);
        removeEffect(player, EffectType.IMMUNE_TO_WITCHHAT);
    }
}
