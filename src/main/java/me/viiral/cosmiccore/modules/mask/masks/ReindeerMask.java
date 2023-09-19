package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class ReindeerMask extends Mask implements EquippableMask {

    public ReindeerMask() {
        super("Reindeer", "");
    }


    @Override
    public String getColor() {
        return CC.Green;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cSPEED IV", "&cFlight regardless of rank");
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
