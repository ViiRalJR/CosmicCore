package me.viiral.cosmiccore.modules.enchantments.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

@Getter
public class EnchantProcOnBlockEvent extends EnchantProcEvent {

    private final Block block;
    private static final HandlerList handlers = new HandlerList();

    public EnchantProcOnBlockEvent(Block block, Enchantment enchantment, int level) {
        super(enchantment, level);
        this.block = block;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
