package me.viiral.cosmiccore.modules.armorsets.events;

import lombok.Getter;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ArmorSetEquipEvent extends Event implements Cancellable {

    private final Player player;
    private final ArmorSet setType;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public ArmorSetEquipEvent(Player player, ArmorSet type) {
        this.player = player;
        this.setType = type;
        this.cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
