package me.viiral.cosmiccore.modules.enchantments.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class EnchantProcOnEquip extends EnchantProcEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public EnchantProcOnEquip(Player player, Enchantment enchantment, int level) {
        super(enchantment, level);
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
