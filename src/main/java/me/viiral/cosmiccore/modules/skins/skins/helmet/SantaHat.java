package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class SantaHat extends Skin {

    public SantaHat() {
        super("Santa Hat", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&c+5% OUTGOING DMG",
                "&c-5% INCOMING DMG"
        );
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(5, event, getName());
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(5, event, getName());
    }
}
