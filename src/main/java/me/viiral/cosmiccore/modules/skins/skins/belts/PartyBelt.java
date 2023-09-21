package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PartyBelt extends Skin implements EquipableSkin {

    public PartyBelt() {
        super("Party Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Green;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cReduce freeze/slowness effects on walk speed by 50%", "&c25% Chance to reduce enemy Rage Stacks by 1 when hit", "&cImmune to Deep Bleed Slowness");
    }


    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_DEEPBLEED_SLOWNESS);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_DEEPBLEED_SLOWNESS);
    }
}
