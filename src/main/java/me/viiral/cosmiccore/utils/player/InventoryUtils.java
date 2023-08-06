package me.viiral.cosmiccore.utils.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class InventoryUtils {

    private InventoryUtils() {

    }

    public static boolean hasAvailableSlot(Player player) {
        Inventory inv = player.getInventory();
        boolean check = false;
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                check = true;
                break;
            }
        }
        return check;
    }

    public static void giveItemToPlayer(Player player, ItemStack itemStack) {
        if (!InventoryUtils.hasAvailableSlot(player)) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }

    public static void giveItemToPlayer(Player player, List<ItemStack> itemStacks) {
        for (ItemStack itemStack : itemStacks) {
            giveItemToPlayer(player, itemStack);
        }
    }

    public static void giveItemToPlayer(Player player, ItemStack[] itemStacks) {
        for (ItemStack itemStack : itemStacks) {
            giveItemToPlayer(player, itemStack);
        }
    }
}
