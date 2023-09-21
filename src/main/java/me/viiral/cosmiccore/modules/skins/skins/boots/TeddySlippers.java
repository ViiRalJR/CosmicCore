package me.viiral.cosmiccore.modules.skins.skins.boots;

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

public class TeddySlippers extends Skin implements EquipableSkin {

    // TODO: 19/09/2023 Nerf enemy special armor and reflect enemy titan trap

    public TeddySlippers() {
        super("Teddy Slippers", SkinType.BOOTS);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cNerf enemy special Armor Set damage by 50%", "&cImmune to enemy (Titan) Trap", "&c-2% Incoming Damage", "&c+1% Outgoing Damage");
    }

    @Override
    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {
        this.getDamageHandler().reduceDamage(2, event, getName());
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(1, event, getName());
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_TRAP);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_TRAP);
    }
}
