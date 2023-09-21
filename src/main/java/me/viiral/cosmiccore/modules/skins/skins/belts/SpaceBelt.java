package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SpaceBelt extends Skin implements EquipableSkin {

    public SpaceBelt() {
        super("Space Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Aqua;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cAuto Bless", "&c+5% Blackscroll Bonus", "&c+2% to not consume Book on apply");
    }

    @Override
    public void onEquip(Player player) {
        PotionEffectUtils.bless(player);
        addEffect(player, EffectType.AUTO_BLESS);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.AUTO_BLESS);
    }
}
