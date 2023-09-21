package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GucciBelt extends Skin {

    public GucciBelt() {
        super("Gucci Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c-25% Incoming Projectile Damage", "&cAll pearls act as escapist pearls");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        if (attacker instanceof Arrow) {
            this.getDamageHandler().reduceDamage(25, event, getName());
        }
    }
}
