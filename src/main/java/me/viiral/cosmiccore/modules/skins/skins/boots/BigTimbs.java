package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class BigTimbs extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 rage stacks, hero killer, pets

    public BigTimbs() {
        super("Big Timbs", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c35% chance on incoming damage to", "&cnegate enemy Rage stack multipliers", "&cImmune to Hero Killer", "&cImmune to enemy outgoing Heroic Pet abilities", "&cGears IV");
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 0, 3);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.SPEED, 3);
    }
}
