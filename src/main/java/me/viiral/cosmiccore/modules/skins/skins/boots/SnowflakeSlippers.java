package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class SnowflakeSlippers extends Skin implements EquipableSkin {

    public SnowflakeSlippers() {
        super("Snowflake Slippers", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cImmune to Teleblock", "&cImmune to Slowness Potion Effect");
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
