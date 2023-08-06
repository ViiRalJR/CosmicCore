package me.viiral.cosmiccore.modules.enchantments.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class EnchantProcOnEntityDeathEvent extends EnchantProcEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Player killer;
    private final LivingEntity victim;

    public EnchantProcOnEntityDeathEvent(Player killer, LivingEntity victim, Enchantment enchantment, int level) {
        super(enchantment, level);
        this.killer = killer;
        this.victim = victim;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
