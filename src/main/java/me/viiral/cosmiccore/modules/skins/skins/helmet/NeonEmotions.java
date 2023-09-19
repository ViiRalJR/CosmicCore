package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class NeonEmotions extends Skin {

    public NeonEmotions() {
        super("Neon Emotions", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&c+6% Outgoing Damage",
                "&c-2% Incoming Damage",
                "&cImmune to Slowness Potion Effect"
        );
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(6, event, getName());
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(2, event, getName());
    }
}
