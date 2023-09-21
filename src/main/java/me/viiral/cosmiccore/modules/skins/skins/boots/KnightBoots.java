package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class KnightBoots extends Skin implements EquipableSkin {

    public KnightBoots() {
        super("Knight Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Gray;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to all but vanilla knockback");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_KNOCKBACK);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_KNOCKBACK);
    }
}
