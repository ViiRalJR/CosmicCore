package me.viiral.cosmiccore.modules.skins.skins.boots;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HazmatBoots extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 Silence pet

    public HazmatBoots() {
        super("Hazmat Boots", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.DarkGreen;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cAuto Bless", "&cChance to activate Silence Pet", " &7(7x7 area for 12 seconds)");
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
