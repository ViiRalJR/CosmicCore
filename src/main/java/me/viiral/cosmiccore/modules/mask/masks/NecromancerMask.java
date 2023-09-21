package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class NecromancerMask extends Mask implements EquippableMask {

    public NecromancerMask() {
        super("Necromancer", "");
    }


    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Lifesteal");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_LIFESTEAL);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_LIFESTEAL);
    }
}
