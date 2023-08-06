package me.viiral.cosmiccore.modules.enchantments.listeners;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.items.SoulGemBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SoulListener implements Listener {
    
    private final SoulManager soulManager;

    public SoulListener(CosmicCore plugin) {
        this.soulManager = plugin.getSoulManager();
    }

    @EventHandler(priority= EventPriority.HIGHEST, ignoreCancelled=true)
    public void onInventoryCombineSoulGems(InventoryClickEvent event) {
        if (!EnchantsAPI.isSoulGem(event.getCursor())) return;
        if (!EnchantsAPI.isSoulGem(event.getCurrentItem())) return;

        Player player = (Player)event.getWhoClicked();

        if (event.getCurrentItem().getAmount() > 1) {
            event.setCancelled(true);
            return;
        }

        SoulGemBuilder cursorSoulGem = new SoulGemBuilder(event.getCursor());
        SoulGemBuilder currentSoulGem = new SoulGemBuilder(event.getCurrentItem());

        int cursorSouls = cursorSoulGem.getSouls();

        event.setCancelled(true);

        if (event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            player.setItemOnCursor(null);
        }

        currentSoulGem.addSouls(cursorSouls);
        event.setCurrentItem(currentSoulGem.build());
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.4f);
        player.updateInventory();

    }

    @EventHandler
    public void onInventoryClickSoulGem(InventoryClickEvent event) {
        if (event.getSlot() < 0) return;

        ItemStack currentItemStack = event.getCurrentItem();
        Player player = ((Player) event.getWhoClicked());

        if (!this.soulManager.isInSoulMode(player)) return;

        int slot = event.getSlot();

        if (EnchantsAPI.isSoulGem(currentItemStack) && (event.getAction().name().startsWith("PICKUP") || event.getAction().name().startsWith("DROP"))) {

            if (slot != this.soulManager.getSoulModeCache(player).getSlot()) {
                return;
            }

            this.soulManager.disableSoulMode(player);
            return;
        }

        if (event.getAction() != InventoryAction.HOTBAR_SWAP) return;

        int hotbarButton = event.getHotbarButton();

        if (hotbarButton == -1) return;
        if (hotbarButton == slot) return;

        ItemStack hotbarItemStack = player.getInventory().getItem(hotbarButton);
        boolean isHotBarItemSoulGem = EnchantsAPI.isSoulGem(hotbarItemStack);
        boolean isCurrentItemSoulGem = EnchantsAPI.isSoulGem(currentItemStack);
        boolean isHotBarSlotDifferentToCacheSlot = hotbarButton != this.soulManager.getSoulModeCache(player).getSlot();
        boolean isCurrentSlotDifferentToCacheSlot = slot != this.soulManager.getSoulModeCache(player).getSlot();

        if (isHotBarItemSoulGem && isCurrentItemSoulGem) {
            if (isCurrentSlotDifferentToCacheSlot && isHotBarSlotDifferentToCacheSlot)
                return;

            if (!(event.getClickedInventory() instanceof PlayerInventory)) {
                this.soulManager.disableSoulMode(player);
                return;
            }


            int newSlot = isCurrentSlotDifferentToCacheSlot ? slot : hotbarButton;
            this.soulManager.getSoulModeCache(player).setSlot(newSlot);
        }

        if (isHotBarItemSoulGem) {
            if (isHotBarSlotDifferentToCacheSlot)
                return;

            if (!(event.getClickedInventory() instanceof PlayerInventory)) {
                this.soulManager.disableSoulMode(player);
                return;
            }

            this.soulManager.getSoulModeCache(player).setSlot(slot);
        }

        if (isCurrentItemSoulGem) {
            if (isCurrentSlotDifferentToCacheSlot)
                return;

            if (!(event.getClickedInventory() instanceof PlayerInventory)) {
                this.soulManager.disableSoulMode(player);
                return;
            }

            this.soulManager.getSoulModeCache(player).setSlot(hotbarButton);
        }
    }

    @EventHandler
    public void onDropSoulsWhileInSoulMode(PlayerDropItemEvent event) {
        if (!EnchantsAPI.isSoulGem(event.getItemDrop().getItemStack())) return;
        Player player = event.getPlayer();
        int slot = player.getInventory().getHeldItemSlot();
        if (this.soulManager.isInSoulMode(player) && slot != this.soulManager.getSoulModeCache(player).getSlot()) {
            return;
        }
        this.soulManager.disableSoulMode(event.getPlayer());
    }

    @EventHandler
    public void onPlayerToggleSoulMode(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!event.hasItem()) return;
        if (!EnchantsAPI.isSoulGem(event.getItem())) return;
        Player player = event.getPlayer();
        int slot = player.getInventory().getHeldItemSlot();

        SoulGemBuilder soulGemBuilder = new SoulGemBuilder(event.getItem());

        if (!this.soulManager.isInSoulMode(player) && soulGemBuilder.getSouls() <= 0) {
            EnchantLanguage.OUT_OF_SOULS.send(player);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.7f, 0.4f);
            return;
        }

        if (this.soulManager.isInSoulMode(player) && slot != this.soulManager.getSoulModeCache(player).getSlot()) {
            EnchantLanguage.CLICKED_WRONG_SOUL_GEM.send(player);
            return;
        }
        this.soulManager.toggleSoulMode(player, soulGemBuilder, slot);
    }

    @EventHandler
    public void onDisconnectWhileInSoulMode(PlayerQuitEvent event) {
        this.soulManager.disableSoulMode(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeathWhileInSoulMode(PlayerDeathEvent event) {
        this.soulManager.disableSoulMode(event.getEntity());
    }
}
