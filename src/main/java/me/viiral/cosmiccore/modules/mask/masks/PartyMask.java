package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class PartyMask extends Mask {

    public PartyMask() {
        super("Party Hat", "");
    }


    @Override
    public String getColor() {
        return CC.White;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+4% DMG", "&c-5% ENEMY DMG");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(4, event, "party_mask");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(5, event, "party_mask");
    }
}
