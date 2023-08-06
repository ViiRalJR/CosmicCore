package me.viiral.cosmiccore.modules.enchantments.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.items.BookBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.items.MiscItems;
import me.viiral.cosmiccore.modules.enchantments.struct.items.SecretDust;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TinkererListeners implements Listener {

    @EventHandler
    public void onClickInTinkerer(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null) return;
        if (!event.getView().getTitle().equals(" You | Tinkerer")) return;
        if (!(event.getClickedInventory() instanceof PlayerInventory) && !event.getView().getTitle().equals(" You | Tinkerer")) return;
        event.setCancelled(true);

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;
        if (!ItemUtils.isEnchantable(event.getCurrentItem()) && !EnchantsAPI.isEnchantBook(event.getCurrentItem())) return;

        if (ItemUtils.isEnchantable(event.getCurrentItem())) {
            NBTItem nbtItem = new NBTItem(event.getCurrentItem());

            if (!this.hasXPValue(nbtItem)) {
                EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());
                nbtItem.setInteger("xp-value", this.generateXPValue(enchantedItemBuilder.getAmountOfEnchantments()));
            }

            event.setCurrentItem(nbtItem.getItem());
        }

        Inventory tinkerer = event.getInventory();

        if (event.getClickedInventory() instanceof PlayerInventory) {
            if (this.isTinkererFull(tinkerer)) return;
            this.addItemToTinkererLeftSide(tinkerer, event.getCurrentItem());

            if (EnchantsAPI.isEnchantBook(event.getCurrentItem())) {
                BookBuilder bookBuilder = new BookBuilder(event.getCurrentItem());
                ItemStack itemStack = new SecretDust(bookBuilder.getBookEnchantment().getTier(), bookBuilder.getDustValue()).build();
                this.addItemToTinkererRightSide(tinkerer, itemStack);
            } else {
                NBTItem nbtItem = new NBTItem(event.getCurrentItem());
                ItemStack itemStack = MiscItems.getTinkererDisplayXpBottle(nbtItem.getInteger("xp-value"));
                this.addItemToTinkererRightSide(tinkerer, itemStack);
            }

            event.setCurrentItem(null);
        } else {
            event.getWhoClicked().getInventory().addItem(event.getCurrentItem());

            int slotToDelete = event.getSlot() <= 5 ? 4 : 5;

            tinkerer.setItem(event.getSlot() + slotToDelete, null);
            event.setCurrentItem(null);
        }
    }

    @EventHandler
    public void onTinkererClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(" You | Tinkerer")) return;
        if (!(event.getPlayer() instanceof Player)) return;
        InventoryUtils.giveItemToPlayer((Player) event.getPlayer(), this.getItemToTinkererLeftSide(event.getInventory()));
    }

    private void addItemToTinkererLeftSide(Inventory tinkerer, ItemStack itemStack) {
        for (int i = 0; i < 54; i += 9) {
            for (int j = 0; j < 4; j++) {
                if (tinkerer.getItem(i + j) != null) continue;
                tinkerer.setItem(i + j, itemStack);
                return;
            }
        }
    }

    private void addItemToTinkererRightSide(Inventory tinkerer, ItemStack itemStack) {
        for (int i = 5; i < 54; i += 9) {
            for (int j = 0; j < 4; j++) {
                if (tinkerer.getItem(i + j) != null) continue;
                tinkerer.setItem(i + j, itemStack);
                return;
            }
        }
    }

    public List<ItemStack> getItemToTinkererLeftSide(Inventory tinkerer) {
        List<ItemStack> itemsList = new ArrayList<>();
        for (int i = 0; i < 54; i += 9) {
            for (int j = 0; j < 4; j++) {
                if (tinkerer.getItem(i + j) == null) continue;
                if (tinkerer.getItem(i + j).getType() == Material.LEGACY_STAINED_GLASS_PANE) continue;
                itemsList.add(tinkerer.getItem(i + j));
            }
        }
        return itemsList;
    }

    private boolean isTinkererFull(Inventory tinkerer) {
        for (int i = 0; i < 54; i += 9) {
            for (int j = 0; j < 5; j++) {
                if (tinkerer.getItem(i + j) != null) continue;
                return false;
            }
        }
        return true;
    }

    private boolean hasXPValue(NBTItem itemStack) {
        return itemStack.hasKey("xp-value");
    }

    private int generateXPValue(int numberOfCustomEnchants) {
        Random random = new Random();

        int bound = numberOfCustomEnchants == 0 ? 180 : numberOfCustomEnchants * 100;
        int min = numberOfCustomEnchants == 0 ? 20 : 300;

        return random.nextInt(bound) + min;
    }
}
