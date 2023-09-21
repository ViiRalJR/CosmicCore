package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class DivineStash extends Skin implements EquipableSkin {

    public DivineStash() {
        super("Divine Stash", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cAuto Bless");
    }

    @Override
    public void onEquip(Player player) {
        PotionEffectUtils.bless(player);
        addEffect(player, EffectType.AUTO_BLESS);
    }

    @Override
    public void onUnequip(Player player) {
        addEffect(player, EffectType.AUTO_BLESS);
    }
}
