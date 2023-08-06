package me.viiral.cosmiccore.modules.enchantments.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;

@Getter
public class EnchantProcOnEntityEvent extends EnchantProcEvent {

    private static final HandlerList handlers = new HandlerList();
    private final LivingEntity attacker;
    private final LivingEntity victim;

    public EnchantProcOnEntityEvent(LivingEntity attacker, LivingEntity victim, Enchantment enchantment, int level) {
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
}
