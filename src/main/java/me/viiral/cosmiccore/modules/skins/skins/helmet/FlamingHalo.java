package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collections;
import java.util.List;

public class FlamingHalo extends Skin {

    public FlamingHalo() {
        super("Flaming Halo", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Yellow;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("+4% Outgoing Damage");
    }


    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(4, event, getName());
    }
}
