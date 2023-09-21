package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RocketBoots extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 Immune to sabotage

    public RocketBoots() {
        super("Rocket Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to Sabotage", "&cGears IV");
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 0, 3);
        addEffect(player, EffectType.IMMUNE_TO_SABOTAGE);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.SPEED, 3);
        removeEffect(player, EffectType.IMMUNE_TO_SABOTAGE);
    }
}
