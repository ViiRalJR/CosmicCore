package me.viiral.cosmiccore.modules.skins.skins.helmet;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HazmatHelmet extends Skin implements EquipableSkin {

    public HazmatHelmet() {
        super("Hazmat Helmet", SkinType.HELMET);
    }

    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("Immune to Freezes");
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_FREEZES);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_FREEZES);
    }
}
