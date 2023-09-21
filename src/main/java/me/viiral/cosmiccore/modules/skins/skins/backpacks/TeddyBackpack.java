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

public class TeddyBackpack extends Skin implements EquipableSkin {


    public TeddyBackpack() {
        super("Teddy Backpack", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&cImmune to (Lethal) Sniper", "&cImmune to Hero Killer", "&c+2% Outgoing Damage");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(2, event, getName());
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_HEROKILLER);
        addEffect(player, EffectType.IMMUNE_TO_SNIPER);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_HEROKILLER);
        removeEffect(player, EffectType.IMMUNE_TO_SNIPER);
    }
}
