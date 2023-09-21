package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HulaSkirt extends Skin implements EquipableSkin {

    public HulaSkirt() {
        super("Hula Skirt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to Lifesteal", "&c+30% more chance to get loot in /warp ruins");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_LIFESTEAL);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_LIFESTEAL);
    }
}
