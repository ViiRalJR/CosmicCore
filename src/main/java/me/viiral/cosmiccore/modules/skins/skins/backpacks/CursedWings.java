package me.viiral.cosmiccore.modules.skins.skins.backpacks;

import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinType;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class CursedWings extends Skin implements EquipableSkin {


    public CursedWings() {
        super("Cursed Wings", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.Gold;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c-75% Silence Duration", "&cBlocks all enemy Rocket Escape", "&c-3% Incoming Damage");
    }

    @Override
    public void onAttacked(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(3, event, getName());
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.BLOCK_ROCKET_ESCAPE);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.BLOCK_ROCKET_ESCAPE);
    }
}
