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
import java.util.Collections;
import java.util.List;

public class DeepspaceBackpack extends Skin implements EquipableSkin {


    public DeepspaceBackpack() {
        super("Deepspace Backpack", SkinType.BACKPACK);
    }

    @Override
    public String getColor() {
        return CC.DarkPurple;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&c+3% Outgoing Damage.", "&cImmune to Soul Trap");
    }

    @Override
    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        this.getDamageHandler().increaseDamage(3, event, getName());
    }

    @Override
    public void onEquip(Player player) {
        addEffect(player, EffectType.IMMUNE_TO_SOULTRAP);
    }

    @Override
    public void onUnequip(Player player) {
        removeEffect(player, EffectType.IMMUNE_TO_SOULTRAP);
    }
}
