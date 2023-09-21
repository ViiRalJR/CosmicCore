package me.viiral.cosmiccore.modules.skins.skins.belts;

import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.SilenceCache;
import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class CupidsHeart extends Skin implements EquipableSkin {

    public CupidsHeart() {
        super("Cupids Heart", SkinType.BELT);
    }


    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cBypass enemy (Paladin) Armored", "&cHoly Aegis VII", "&c+10% Outgoing Damage to Silenced enemies");
    }


    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.BLOCK_ARMORED);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.BLOCK_ARMORED);
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        SilenceCache silenceCache = CacheUtils.getSilenceCache((Player) attacked);
        if (silenceCache.isSilenced()) {
            this.getDamageHandler().increaseDamage(10, event, getName());
        }
    }
}
