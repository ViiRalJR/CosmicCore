package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class BarrysBolts extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 bloody deep wounds

    public BarrysBolts() {
        super("Barrys Bolts", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cImmune to Slowness Potion Effect", "&c-15% damage from enemy (Bloody) Deep Wounds");
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 0, 3);
        addEffect(player, EffectType.IMMUNE_TO_SLOWNESS);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.SPEED, 3);
        removeEffect(player, EffectType.IMMUNE_TO_SLOWNESS);
    }
}
