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

public class BloodstainedGaloshes extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 Execute, cage, immune to cage

    public BloodstainedGaloshes() {
        super("Bloodstained Galoshes", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cImmune to World Destroyer Cage", "&c25% chance to reflect cage back at the user", "&cImmune to Slowness/Freezes", "Increases Execute HP threshold to proc by 1.3x");
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 0, 3);
        addEffect(player, EffectType.IMMUNE_TO_FREEZES);
        addEffect(player, EffectType.IMMUNE_TO_SLOWNESS);
        addEffect(player, EffectType.IMMUNE_TO_CAGE);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.SPEED, 3);
        removeEffect(player, EffectType.IMMUNE_TO_FREEZES);
        removeEffect(player, EffectType.IMMUNE_TO_SLOWNESS);
        removeEffect(player, EffectType.IMMUNE_TO_CAGE);
    }
}
