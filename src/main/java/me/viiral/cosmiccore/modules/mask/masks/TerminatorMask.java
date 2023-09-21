package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class TerminatorMask extends Mask implements EquippableMask {

    public TerminatorMask() {
        super("Terminator", "");
    }


    @Override
    public String getColor() {
        return CC.DarkRed;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cNegate 50% Shockwave, Ragdoll, Rocket Escape", "&cSpeed IV");
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 0, 3);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.SPEED, 3);
    }
}
