package me.viiral.cosmiccore.modules.enchantments.listeners;

import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HolyWhiteScrollListener implements Listener {

    // Store the Holy Protected items for each player upon death
    private final Map<UUID, Set<ItemStack>> holyProtectedItems = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Set<ItemStack> protectedItems = Stream.of(player.getInventory().getContents())
                .filter(item -> item != null && new EnchantedItemBuilder(item).isHolyProtected())
                .peek(item -> {
                    EnchantedItemBuilder builder = new EnchantedItemBuilder(item);
                    builder.setHolyProtected(false);  // Setting HolyProtected to false
                    event.getDrops().remove(item); // Preventing the HolyProtected item from dropping
                })
                .collect(Collectors.toSet());

        if (!protectedItems.isEmpty()) {
            holyProtectedItems.put(player.getUniqueId(), protectedItems);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (holyProtectedItems.containsKey(playerId)) {
            holyProtectedItems.get(playerId).forEach(player.getInventory()::addItem);
            holyProtectedItems.remove(playerId);
        }
    }
}

