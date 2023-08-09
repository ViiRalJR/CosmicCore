package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class DragonMask extends Mask {

    public DragonMask() {
        super("Dragon", "");
    }


    @Override
    public String getColor() {
        return CC.DarkRed;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+5$ DMG", "&cImmune to Fire and Lava damage");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(5, event, "dragon_mask");
    }

}
