package me.viiral.cosmiccore.modules.enchantments;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEquip;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantmentCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Set;

import static me.viiral.cosmiccore.modules.NbtTags.*;


public class EnchantsAPI {

    public static boolean isRandomEnchantBook(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.BOOK) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(BOOK_DATA_STRING);
    }

    public static EnchantTier getRandomEnchantBookTier(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return EnchantTier.valueOf(nbtItem.getString(BOOK_TIER_DATA_STRING).toUpperCase(Locale.ROOT));
    }

    public static boolean isCustomItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return (nbtItem.hasKey(COSMIC_DATA_STRING));
    }

    public static boolean isEnchantBook(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.BOOK) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(ENCHANT_BOOK_DATA_STRING);
    }

    public static boolean isSoulTracker(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.PAPER) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(SOUL_TRACKER_DATA_STRING);
    }

    public static boolean isSecretDust(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.FIRE_CHARGE) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(SECRET_DUST_DATA_STRING);
    }

    public static boolean isEnchantmentOrb(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.ENDER_EYE) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(ORB_DATA_STRING);
    }

    public static boolean isSoulGem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.EMERALD) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(SOUL_GEM_DATA_STRING);
    }

    public static boolean isDust(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.SUGAR && itemStack.getType() != Material.GLOWSTONE_DUST) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(DUST_DATA_STRING);
    }

    public static boolean isBlackScroll(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.INK_SAC) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(BLACK_SCROLL_DATA_STRING);
    }

    public static boolean isWhiteScroll(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.MAP) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(WHITE_SCROLL_DATA_STRING);
    }

    public static boolean isTransmogScroll(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.PAPER) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(TRANSMOG_SCROLL_DATA_STRING);
    }

    public static boolean isRandomizationScroll(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.PAPER) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(RANDOMIZATION_SCROLL_DATA_STRING);
    }

    public static boolean isSoulPearl(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.ENDER_PEARL) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(SOUL_PEARL_DATA_STRING);
    }

    public static boolean isRenameTag(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.NAME_TAG) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(COSMIC_DATA_STRING).equals(RENAME_TAG_DATA_STRING);
    }

    public static Set<Enchantment> getEnchantments(Player player) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchants();
    }

    public static void addEnchantmentToPlayer(Player player, Enchantment enchantment, int level) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        enchantmentCache.addEnchantment(enchantment, level);
    }

    public static void removeEnchantmentFromPlayer(Player player, Enchantment enchantment, int level) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        enchantmentCache.removeEnchantment(enchantment, level);
    }

    public static boolean hasEnchantment(Player player, Enchantment enchantment) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.hasEnchantment(enchantment);
    }

    public static boolean hasEnchantment(Player player, String enchantmentID) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.hasEnchantment(enchantmentID);
    }

    public static Integer getEnchantmentLevel(Player player, Enchantment enchantment) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchantInfo(enchantment).getLevel();
    }

    public static Integer getEnchantmentLevel(Player player, String enchantmentID) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchantInfo(enchantmentID).getLevel();
    }

    public static Integer getEnchantmentAmount(Player player, Enchantment enchantment) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchantInfo(enchantment).getAmount();
    }

    public static Integer getEnchantmentAmount(Player player, String enchantmentID) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchantInfo(enchantmentID).getAmount();
    }

    public static EnchantInfo getEnchantmentInfo(Player player, Enchantment enchantment) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchantInfo(enchantment);
    }

    public static EnchantInfo getEnchantmentInfo(Player player, String enchantmentID) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        return enchantmentCache.getEnchantInfo(enchantmentID);
    }

    private static EnchantmentCache getEnchantCache(Player player) {
        EnchantmentCache enchantmentCache = (EnchantmentCache) CacheManager.getInstance().getCachedPlayer(player).getCache(ENCHANTMENT_CACHE_ID);

        if (enchantmentCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new EnchantmentCache());

        return(EnchantmentCache) CacheManager.getInstance().getCachedPlayer(player).getCache(ENCHANTMENT_CACHE_ID);
    }

    public static void clearEnchants(Player player) {
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        enchantmentCache.clearEnchantments();
    }

    public static void clearEnchantsFromCacheFromOneArmorItem(Player player, ItemStack itemStack) {
        if (!ItemUtils.isArmor(itemStack)) return;
        EnchantmentCache enchantmentCache = getEnchantCache(player);
        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);
        enchantedItemBuilder.getEnchantments().forEach((enchantment, level) -> {
            enchantmentCache.removeEnchantment(enchantment, level);
            if (enchantment instanceof ArmorEquipEventEnchant) {
                ArmorEquipEventEnchant equipEventEnchant = ((ArmorEquipEventEnchant) enchantment);
                equipEventEnchant.runArmorUnEquipEvent(player, level);
            }
        });
    }

    public static void reprocEnchants(Player player) {
        if (player == null || player.isDead()) return;
        clearEnchants(player);
        player.getActivePotionEffects().clear();
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (!ItemUtils.isEnchantable(itemStack)) continue;
            EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);
            enchantedItemBuilder.getEnchantments().forEach((enchant, level) -> {
                if (enchant instanceof ArmorEquipEventEnchant) {
                    EnchantProcEvent procEvent = new EnchantProcOnEquip(player, enchant, level);
                    Bukkit.getPluginManager().callEvent(procEvent);
                    if(procEvent.isCancelled()) return;
                    ((ArmorEquipEventEnchant) enchant).runArmorEquipEvent(player, level);
                }
                addEnchantmentToPlayer(player, enchant, level);
            });
        }
    }

    public static void reprocEnchantsFromOneArmorItem(Player player, ItemStack itemStack) {
        if (player == null || player.isDead()) return;
        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);
        enchantedItemBuilder.getEnchantments().forEach((enchant, level) -> {
            if (enchant instanceof ArmorEquipEventEnchant) {
                EnchantProcEvent procEvent = new EnchantProcOnEquip(player, enchant, level);
                Bukkit.getPluginManager().callEvent(procEvent);
                if(procEvent.isCancelled()) return;
                ((ArmorEquipEventEnchant) enchant).runArmorEquipEvent(player, level);
            }
            addEnchantmentToPlayer(player, enchant, level);
        });
    }
}
