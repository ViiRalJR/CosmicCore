package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class ChainBelt extends Skin implements EquipableSkin {

    public ChainBelt() {
        super("Chain Belt", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Gray;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cAuto Bless", "&c200% damage to Citadel Siege blocks", "&c+2.5% Outgoing Damage");
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

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(2.5, event, getName());
    }
}
