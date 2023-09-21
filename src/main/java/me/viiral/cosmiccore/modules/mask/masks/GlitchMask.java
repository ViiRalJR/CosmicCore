package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GlitchMask extends Mask implements EquippableMask {

    public GlitchMask() {
        super("Glitch", "");
    }


    @Override
    public String getColor() {
        return CC.White;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Teleblock");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_TELEBLOCK);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_TELEBLOCK);
    }
}
