package me.viiral.cosmiccore.modules.enchantments.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

@Getter
public class EnchantProcOnDamageEvent extends EnchantProcEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final EntityDamageEvent.DamageCause damageCause;

    public EnchantProcOnDamageEvent(Player player, EntityDamageEvent.DamageCause damageCause, Enchantment enchantment, int level) {
        super(enchantment, level);
        this.damageCause = damageCause;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
