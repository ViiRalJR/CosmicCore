package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class RollerSkates extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 Immune to skrt and monopoly chance

    public RollerSkates() {
        super("Roller Skates", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cGears IV", "&cAuto Bless", "&cImmune to Skrrt Pet", "&c+10% Monopoly Chance");
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
