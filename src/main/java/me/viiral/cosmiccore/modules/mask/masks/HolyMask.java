package me.viiral.cosmiccore.modules.mask.masks;

import com.sun.crypto.provider.DESParameters;
import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HolyMask extends Mask implements EquippableMask {

    public HolyMask() {
        super("Holy", "");
    }


    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cImmune to Bleed slowness");
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
