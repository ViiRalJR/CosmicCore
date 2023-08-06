package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ItemInteractEventEnchant extends Enchantment {

    public ItemInteractEventEnchant(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) return;

        ItemStack itemStack = event.getItem();

        if (itemStack == null) return;
        if (!this.getType().getItems().contains(itemStack.getType())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasEnchantment(this)) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            this.runInteractRightClickEvent(event, player, itemStack, enchantedItemBuilder);

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
            this.runInteractLeftClickEvent(event, player, itemStack, enchantedItemBuilder);
    }

    public abstract void runInteractRightClickEvent(PlayerInteractEvent event, Player player, ItemStack itemStack, EnchantedItemBuilder enchantedItemBuilder);
    public abstract void runInteractLeftClickEvent(PlayerInteractEvent event, Player player, ItemStack itemStack, EnchantedItemBuilder enchantedItemBuilder);
}
