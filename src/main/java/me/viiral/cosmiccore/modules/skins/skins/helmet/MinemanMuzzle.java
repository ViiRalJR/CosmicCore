package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MinemanMuzzle extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 this

    public MinemanMuzzle() {
        super("Mineman Muzzle", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&c25% chance to curse enemies with +33% Incoming",
                "&cDamage when their Phoenix procs for 10s",
                "&7 (Can only proc once every 10m)",
                "&c3.5% chance to negate enemy Cosmic Cape for 10s",
                "&cImmune to Banshee RKO",
                "&c10% chance to reflect damage to enemies in a 3x3 radius"
        );
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_BANSHEE_HOLY);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_BANSHEE_HOLY);
    }
}
