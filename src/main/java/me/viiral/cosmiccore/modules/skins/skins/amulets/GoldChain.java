package me.viiral.cosmiccore.modules.skins.skins.amulets;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GoldChain extends Skin implements EquipableSkin {

    public GoldChain() {
        super("40K Gold Chain", SkinType.AMULET);
    }

    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&c+2% /sell prices");
        return lore;
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.TWO_SELL_PRICES);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.TWO_SELL_PRICES);
    }
}
