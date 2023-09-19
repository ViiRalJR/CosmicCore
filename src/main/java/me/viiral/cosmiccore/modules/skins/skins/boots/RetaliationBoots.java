package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class RetaliationBoots extends Skin {

    // TODO: 19/09/2023 More damage to bossses

    public RetaliationBoots() {
        super("Retaliation Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+10% Outgoing Damage to Bosses", "&c+10% Incoming Damage");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(-10, event, getName());
    }
}
