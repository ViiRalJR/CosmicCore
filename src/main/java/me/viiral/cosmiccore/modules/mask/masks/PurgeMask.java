package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collections;
import java.util.List;

public class PurgeMask extends Mask {

    public PurgeMask() {
        super("Purge", "");
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c+2.5% DMG");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(2.5, event, "purge_mask");
    }
}
