package me.viiral.cosmiccore.modules.combat;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@Getter
public class CombatManager {

    private final Map<UUID, Vector> playerKnockbackMap;


    public CombatManager() {
        this.playerKnockbackMap = new WeakHashMap<>();
    }

    public void setAttackSpeed(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute == null) return;
        attribute.setBaseValue(40);
        player.saveData();
    }

    public void setAttackDelay(Player player) {
        player.setMaximumNoDamageTicks(20);
    }


    public boolean isElytra(ItemStack item) {
        return item != null && item.getType() == Material.ELYTRA;
    }

}
