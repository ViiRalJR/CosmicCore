package me.viiral.cosmiccore.modules.enchantments.listeners;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.HeroicEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Heroicable;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.items.BookBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import me.viiral.cosmiccore.utils.RomanNumeral;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Random;

public class BookListeners implements Listener {
    
    private final EnchantRegister enchantRegister;

    public BookListeners(CosmicCore plugin) {
        this.enchantRegister = plugin.getEnchantRegister();
        this.initParticles();

    }
    
    @EventHandler
    public void onRightClickRandomBook(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!EnchantsAPI.isRandomEnchantBook(event.getItem())) return;

        Player player = event.getPlayer();

        if (!InventoryUtils.hasAvailableSlot(player)) {
            EnchantLanguage.FULL_INVENTORY.send(player);
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 0.7f, 1.4f);
            return;
        }

        ItemStack itemStack = event.getItem();

        EnchantTier enchantTier = EnchantsAPI.getRandomEnchantBookTier(itemStack);

        Enchantment enchantment = this.enchantRegister.selectRandomEnchantment(enchantTier);

        if (enchantment == null) return;

        BookBuilder bookBuilder = new BookBuilder(enchantment);
        ItemStack book = bookBuilder.build();

        player.getInventory().addItem(book);
        player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 0.7f, 1.4f);

        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else {
            event.getPlayer().setItemInHand(null);
        }

        EnchantLanguage.BOOK_OPEN.send(player, str -> str
                .replace("{book-type}", enchantTier.getColor() + enchantTier.getFormatedName())
                .replace("{book}", enchantTier.getColor() + "&n&l" + enchantment.getName() + " " + RomanNumeral.convertToRoman(bookBuilder.getBookLevel()))
        );

        player.updateInventory();
    }

    @EventHandler
    public void onBookRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!EnchantsAPI.isEnchantBook(event.getItem())) return;

        EnchantLanguage.BOOK_RIGHT_CLICK.send(event.getPlayer());
    }

    @EventHandler
    public void onBookApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!EnchantsAPI.isEnchantBook(event.getCursor()) || !ItemUtils.isEnchantable(event.getCurrentItem())) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(event.getCurrentItem());
        BookBuilder bookBuilder = new BookBuilder(event.getCursor());
        Enchantment enchantment = bookBuilder.getBookEnchantment();
        int level = bookBuilder.getBookLevel();
        switch (this.applyEnchantment(enchantedItemBuilder, bookBuilder)) {
            case FAIL:
                event.setCancelled(true);
                event.setCursor(null);
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 5.0F, 0.1F);
                break;

            case HEROIC_SUCCESS:
                event.setCancelled(true);
                Enchantment remove = CosmicCore.getInstance().getEnchantRegister().getEnchantmentFromID(bookBuilder.getRequired());
                enchantedItemBuilder.removeEnchantment(remove);
                enchantedItemBuilder.addEnchantment(enchantment, level);
                event.setCurrentItem(enchantedItemBuilder.build());
                event.setCursor(null);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 0.75f);
                break;

            case REQUIRED:
                event.setCancelled(true);
                EnchantLanguage.DOESNT_HAVE_REQUIRED.send(player);
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 5.0F, 0.1F);
                break;

            case SUCCESS:
                event.setCancelled(true);
                enchantedItemBuilder.addEnchantment(enchantment, level);
                event.setCurrentItem(enchantedItemBuilder.build());
                event.setCursor(null);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 0.75f);
                break;
        }
    }

    private EnchantResult applyEnchantment(EnchantedItemBuilder enchantedItemBuilder, BookBuilder bookBuilder) {
        Enchantment enchantment = bookBuilder.getBookEnchantment();


        if (enchantedItemBuilder.hasEnchantment(enchantment))
            if (enchantedItemBuilder.getEnchantmentLevel(enchantment) > bookBuilder.getBookLevel())
                return EnchantResult.CONTAINS;
            else
                return EnchantResult.CONTAINS_BUT_UPGRADE;

        if (enchantedItemBuilder.build().getAmount() > 1 || bookBuilder.build().getAmount() > 1)
            return EnchantResult.INVALID_COUNT;

        if (!enchantment.getType().getItems().contains(enchantedItemBuilder.build().getType())) {
            return EnchantResult.INVALID_TYPE;
        }

        if (enchantedItemBuilder.getAmountOfEnchantments() + 1 > enchantedItemBuilder.getSlots()) {
            return EnchantResult.NOT_ENOUGH_SLOTS;
        }

        if (bookBuilder.getBookEnchantment() instanceof Heroicable) {
            Heroicable h = (Heroicable) bookBuilder.getBookEnchantment();
            if (enchantedItemBuilder.hasEnchantment(h.getHeroicEnchant()))
                if (enchantedItemBuilder.getEnchantmentLevel(enchantment) > bookBuilder.getBookLevel())
                    return EnchantResult.CONTAINS;
                else
                    return EnchantResult.CONTAINS_BUT_UPGRADE;

        }
        if (bookBuilder.isHeroic()) {
            if (!enchantedItemBuilder.hasEnchantment(bookBuilder.getRequired())) {
                return EnchantResult.REQUIRED;
            }
            if (bookBuilder.getSuccessRate() > this.generateChance()) {
                return EnchantResult.HEROIC_SUCCESS;
            }
        } else {

            if (bookBuilder.getSuccessRate() > this.generateChance()) {
                return EnchantResult.SUCCESS;
            }
        }

        if (bookBuilder.getDestroyRate() > this.generateChance()) {

            if (enchantedItemBuilder.isProtected()) {
                return EnchantResult.PROTECTED;
            }

            return EnchantResult.DESTROY;
        }

        return EnchantResult.FAIL;
    }

    private enum EnchantResult {
        HEROIC_SUCCESS, SUCCESS, FAIL, DESTROY, INVALID_COUNT, INVALID_TYPE, CONTAINS, CONTAINS_BUT_UPGRADE, NOT_ENOUGH_SLOTS, PROTECTED, REQUIRED
    }

    private int generateChance() {
        Random random = new Random();
        return random.nextInt(101);
    }

    private void initParticles() {

    }
}
