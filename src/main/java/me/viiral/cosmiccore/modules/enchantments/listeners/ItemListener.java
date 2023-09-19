package me.viiral.cosmiccore.modules.enchantments.listeners;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulTrackerCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.items.*;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.utils.StringUtils;
import me.viiral.cosmiccore.utils.TimeUtils;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemListener implements Listener {

    private final List<Player> renameTagList;

    private final SoulManager soulManager;

    public ItemListener(CosmicCore plugin) {
        this.renameTagList = new ArrayList<>();
        this.soulManager = plugin.getSoulManager();
    }

    @EventHandler
    public void onDustApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isDust(event.getCursor()) || !EnchantsAPI.isEnchantBook(event.getCurrentItem()))
            return;

        event.setCancelled(true);

        BookBuilder bookBuilder = new BookBuilder(event.getCurrentItem());

        if (bookBuilder.getSuccessRate() == 100) return;

        DustBuilder dustBuilder = new DustBuilder(event.getCursor());

        if (dustBuilder.getEnchantTier() != bookBuilder.getBookEnchantment().getTier()) return;

        ItemStack itemStack = bookBuilder.addSuccessRate(dustBuilder.getPercent()).build();

        event.setCurrentItem(itemStack);

        if (event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            event.setCursor(null);
        }
    }

    @EventHandler
    public void onBlackScrollApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isBlackScroll(event.getCursor()) || !ItemUtils.isEnchantable(event.getCurrentItem()))
            return;

        event.setCancelled(true);

        if (!InventoryUtils.hasAvailableSlot(player)) {
            EnchantLanguage.FULL_INVENTORY.send(player);
            return;
        }

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());

        if (!enchantedItemBuilder.hasEnchantments()) {
            EnchantLanguage.DOESNT_HAVE_ANY_ENCHANTMENTS.send(player);
            return;
        }

        BlackScrollBuilder blackScrollBuilder = new BlackScrollBuilder(event.getCursor());
        Enchantment enchantToRemove = enchantedItemBuilder.selectRandomEnchantment();
        BookBuilder bookBuilder = new BookBuilder(enchantToRemove, enchantedItemBuilder.getEnchantmentLevel(enchantToRemove));

        enchantedItemBuilder.removeEnchantment(enchantToRemove);

        player.getInventory().addItem(bookBuilder.setSuccessRate(blackScrollBuilder.getPercent()).setDestroyRate(100 - blackScrollBuilder.getPercent()).build());

        event.setCurrentItem(enchantedItemBuilder.build());
        if (event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            event.setCursor(null);
        }
    }

    @EventHandler
    public void onOrbApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isEnchantmentOrb(event.getCursor()) || !ItemUtils.isEnchantable(event.getCurrentItem()))
            return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());
        OrbBuilder orbBuilder = new OrbBuilder(event.getCursor());
        int slots = orbBuilder.getSlots();
        int success = orbBuilder.getSuccessRate();

        event.setCancelled(true);

        if (!orbBuilder.getOrbType().canBeAppliedToItem(event.getCurrentItem())) {
            return;
        }

        if (enchantedItemBuilder.getSlots() >= orbBuilder.getSlots()) {
            return;
        }

        event.setCursor(null);

        if (success < this.generateChance()) {
            return;
        }

        enchantedItemBuilder.setSlots(slots);
        enchantedItemBuilder.updateSlotsLore();
        event.setCurrentItem(enchantedItemBuilder.build());
    }

    @EventHandler
    public void onWhiteScrollApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isWhiteScroll(event.getCursor()) || !ItemUtils.isEnchantable(event.getCurrentItem()))
            return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());

        event.setCancelled(true);

        if (enchantedItemBuilder.isProtected()) {
            return;
        }

        if(event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            event.setCursor(null);
        }

        enchantedItemBuilder.setProtected(true);
        event.setCurrentItem(enchantedItemBuilder.build());
    }

    @EventHandler
    public void onWhiteScrollClick(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if (!EnchantsAPI.isWhiteScroll(event.getItem())) return;

        event.setCancelled(true);
        event.getPlayer().updateInventory();
    }

    @EventHandler
    public void onTransmogScrollApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isTransmogScroll(event.getCursor()) || !ItemUtils.isEnchantable(event.getCurrentItem()))
            return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());

        event.setCancelled(true);

        if (!enchantedItemBuilder.hasEnchantments()) {
            return;
        }

        if (event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            event.setCursor(null);
        }

        event.setCurrentItem(enchantedItemBuilder.sortEnchantLore().addEnchantAmountToName().build());
    }

    @EventHandler
    public void onRandomizationScrollApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isRandomizationScroll(event.getCursor()) || !EnchantsAPI.isEnchantBook(event.getCurrentItem()))
            return;

        BookBuilder bookBuilder = new BookBuilder(event.getCurrentItem());
        RandomizationScrollBuilder randomizationScrollBuilder = new RandomizationScrollBuilder(event.getCursor());

        event.setCancelled(true);

        if (bookBuilder.getBookEnchantment().getTier() != randomizationScrollBuilder.getTier()) return;

        if (event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            event.setCursor(null);
        }

        event.setCurrentItem(bookBuilder.setSuccessRate(this.generateChance()).setDestroyRate(this.generateChance()).build());
    }

    @EventHandler
    public void onSoulTrackerApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isSoulTracker(event.getCursor()) || !ItemUtils.isWeapon(event.getCurrentItem()))
            return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());
        SoulTrackerBuilder soulTrackerBuilder = new SoulTrackerBuilder(event.getCursor());

        event.setCancelled(true);

        if (enchantedItemBuilder.hasSoulTrackerAlreadyApplied()) {
            EnchantLanguage.ALREADY_HAS_SOUL_TRACKER.send(player);
            return;
        }

        if (event.getCursor().getAmount() > 1) {
            event.getCursor().setAmount(event.getCursor().getAmount() - 1);
        } else {
            event.setCursor(null);
        }

        event.setCurrentItem(enchantedItemBuilder.applySoulTracker(soulTrackerBuilder).build());
        EnchantLanguage.SOUL_TRACKER_APPLIED.send(player, str -> str
                .replace("{soul-tracker}", soulTrackerBuilder.getEnchantTier().getColor() + "&l" + soulTrackerBuilder.getEnchantTier().getFormatedName())
                .replace("{item}", StringUtils.toNiceString(event.getCurrentItem().getType().name()))
        );
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        this.renameTagList.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!this.renameTagList.contains(event.getPlayer())) return;
        event.setCancelled(true);

        Player player = event.getPlayer();

        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            EnchantLanguage.EMPTY_HAND.send(player);
            return;
        }

        if (!ItemUtils.isEnchantable(event.getPlayer().getItemInHand())) return;

        this.renameTagList.remove(event.getPlayer());
        EnchantLanguage.SUCCESSFULLY_RENAMED.send(player, str -> str.replace("{new-name}", CC.translate(event.getMessage())));
        player.setItemInHand(new ItemBuilder(player.getItemInHand()).setName(event.getMessage()).colorizeName().build());
    }

    @EventHandler
    public void onPlayerDeathUpdateSoulTracker(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        if (killer.getItemInHand() == null) return;

        ItemStack itemStack = killer.getItemInHand();

        if (!ItemUtils.isWeapon(itemStack)) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        if (!enchantedItemBuilder.hasSoulTrackerAlreadyApplied()) return;

        SoulTrackerCache soulTrackerCache = CacheUtils.getSoulTrackerCache(killer);

        if (soulTrackerCache.isOnHarvestCooldown(event.getEntity())) {
            EnchantLanguage.SOUL_HARVEST_COOLDOWN.send(killer, str -> str
                    .replace("{player}", event.getEntity().getName())
                    .replace("{time}", TimeUtils.formatSeconds(soulTrackerCache.getSecondsLeftOnHarvestCooldown(event.getEntity())))
            );
            return;
        }

        soulTrackerCache.addHarvestCooldown(event.getEntity());

        enchantedItemBuilder.incrementHarvestedSouls();
        enchantedItemBuilder.updateSoulsHarvested();

        killer.setItemInHand(enchantedItemBuilder.build());
        killer.updateInventory();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRenameTagClick(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if (!EnchantsAPI.isRenameTag(event.getItem())) return;

        event.setCancelled(true);
        Player player = event.getPlayer();

        if (this.renameTagList.contains(player)) return;

        if (event.getItem().getAmount() > 1) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }

        this.renameTagList.add(player);
        EnchantLanguage.RENAME_TAG_CLICKED.send(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onSecretDustClick(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if (!EnchantsAPI.isSecretDust(event.getItem())) return;

        event.setCancelled(true);
        Player player = event.getPlayer();

        if (!InventoryUtils.hasAvailableSlot(player)) {
            EnchantLanguage.FULL_INVENTORY.send(player);
            return;
        }

        if (event.getItem().getAmount() > 1) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }

        SecretDust secretDust = new SecretDust(event.getItem());
        player.getInventory().addItem(secretDust.getDust());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onSoulPearlClick(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if (!EnchantsAPI.isSoulPearl(event.getItem())) return;

        Player player = event.getPlayer();
        SoulModeCache soulModeCache = this.soulManager.getSoulModeCache(player);

        if (soulModeCache == null || soulModeCache.getSouls() < 5) {
            event.setCancelled(true);
            player.setItemInHand(event.getItem());
            player.updateInventory();
            EnchantLanguage.OUT_OF_SOULS.send(player);
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
            return;
        }

        if (player.getItemInHand() == null) {
            player.setItemInHand(new SoulPearlBuilder().build());
        } else {
            player.getItemInHand().setAmount(event.getItem().getAmount() + 1);
        }

        player.updateInventory();

        soulModeCache.getSoulGemBuilder().removeSouls(5);
        soulModeCache.updateSoulGem();
    }

    private int generateChance() {
        Random random = new Random();
        return random.nextInt(101);
    }
}
