package me.viiral.cosmiccore.modules.enchantments.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
public class SlownessEnchantProc extends EnchantProcEvent implements Cancellable {

    private boolean cancelled;
    private final LivingEntity attacker;
    private final LivingEntity victim;

    private static final HandlerList handlers = new HandlerList();

    public SlownessEnchantProc(LivingEntity attacker, LivingEntity victim, Enchantment enchantment, int level) {
        super(enchantment, level);
        this.attacker = attacker;
        this.victim = victim;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
