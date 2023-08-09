package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class DeathKnightMask extends Mask {

    public DeathKnightMask() {
        super("Death Knight", "");
    }


    @Override
    public String getColor() {
        return CC.DarkAqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c50% chance to negate enemy's phoenix", "&c+2.5% ENEMY DMG");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(2.5, event, "death_knight");
    }
}
