package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TurkeyMask extends Mask {

    public TurkeyMask() {
        super("Turkey", "");
    }


    @Override
    public String getColor() {
        return CC.Yellow;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c+2% Dodge");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        if ((new Random().nextInt(100) + 1) < 2) {
            attacker.sendMessage(CC.YellowB + "*** INCOMING ATTACK DODGED (TURKEY MASK) ***");
            event.setCancelled(true);
        }
    }
}
