package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class CorruptBelt extends Skin implements EquipableSkin {

    public CorruptBelt() {
        super("Corrupt Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cGodly Overload V");
    }

    @Override
    public void onEquip(Player player) {
        addPotionEffect(player, PotionEffectType.HEALTH_BOOST, 0, 4);
    }

    @Override
    public void onUnequip(Player player) {
        removePotionEffect(player, PotionEffectType.HEALTH_BOOST, 4);
    }
}
