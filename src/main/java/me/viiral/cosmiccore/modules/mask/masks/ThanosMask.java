package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class ThanosMask extends Mask {

    public ThanosMask() {
        super("Thanos", "");
    }


    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+5% DMG", "&cImmune to Natures Wrath", "&c100% chance to negate enemy's Phoenix", "&c15% Holy White Scroll negation");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(5, event, "thanos_mask");
    }
}
