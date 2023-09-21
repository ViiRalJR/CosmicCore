package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class UtilityBelt extends Skin implements EquipableSkin {

    public UtilityBelt() {
        super("Utility Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cPhoenix cannot be blocked", "&cAuto Feign Death (with Phoenix)");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.CANT_BLOCK_PHOENIX);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.CANT_BLOCK_PHOENIX);
    }
}
