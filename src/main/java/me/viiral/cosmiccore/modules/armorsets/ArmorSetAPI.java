package me.viiral.cosmiccore.modules.armorsets;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSetRegister;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorCrystalCache;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorSetCache;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import me.viiral.cosmiccore.utils.cache.CachedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.viiral.cosmiccore.modules.NbtTags.*;

public class ArmorSetAPI {

    public static ArmorSet getSetTypeOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return null;
        NBTItem wrapped = new NBTItem(item);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");
        return cosmicData.hasTag("armorSetType") ? ArmorSetRegister.getInstance().getArmorSetFromID(cosmicData.getString("armorSetType")) : null;
    }

    public static List<ArmorSet> getCrystalTypeOnItem(ItemStack itemStack) {
        return getCrystalTypesFromString(getCrystalIDOnItem(itemStack));
    }

    public static String getCrystalIDOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return null;
        NBTItem wrapped = new NBTItem(item);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");
        return cosmicData.hasTag("crystalType") ? cosmicData.getString("crystalType") : null;
    }

    public static List<ArmorSet> getCrystalTypesFromString(String str) {
        if (str == null) return null;
        List<ArmorSet> sets = new ArrayList<>();
        String[] split = str.split("@;;");
        for (String loop : split) {
            if (CosmicCore.getInstance().getArmorSetRegister().isArmorSet(loop)) {
                sets.add(CosmicCore.getInstance().getArmorSetRegister().getArmorSetFromID(loop));
            }
        }
        return sets;
    }

    public static boolean hasSetTypeOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return false;
        NBTItem wrapper = new NBTItem(item);
        NBTCompound cosmicData = wrapper.getOrCreateCompound("cosmicData");
        return cosmicData.hasTag("armorSetType");
    }

    public static boolean hasCrystalTypeOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return false;
        NBTItem wrapped = new NBTItem(item);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");
        return cosmicData.hasTag("crystalType");
    }

    public static ArmorSet getCurrentArmorSet(Player player) {
        CachedPlayer cachedPlayer = CacheManager.getInstance().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache) cachedPlayer.getCache("armor_set");
        return cache.getCurrentArmorSet();
    }

    public static HashMap<ArmorSet, Integer> getCurrentArmorCrystals(Player player) {
        CachedPlayer cachedPlayer = CacheManager.getInstance().getCachedPlayer(player);
        ArmorCrystalCache cache = (ArmorCrystalCache) cachedPlayer.getCache("armor_crystal");
        return cache.getCrystals();
    }

    public static void refreshArmorCrystals(Player player) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        ArmorCrystalCache cache = (ArmorCrystalCache) (cachedPlayer.hasCache("armor_crystal") ? cachedPlayer.getCache("armor_crystal") : cachedPlayer.registerCache(new ArmorCrystalCache(player)));
        ItemStack[] armor = player.getInventory().getArmorContents();
        cache.clear();
        for (ItemStack item : armor) {
            if (ArmorSetAPI.hasCrystalTypeOnItem(item))
                for (ArmorSet set : ArmorSetAPI.getCrystalTypesFromString(ArmorSetAPI.getCrystalIDOnItem(item))) {
                    cache.addArmorCrystal(set);
                }

        }
    }

    public static boolean isArmorCrystal(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.NETHER_STAR) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getOrCreateCompound(COSMIC_DATA_STRING).getString(ARMOR_CRYSTAL) != null && !nbtItem.getOrCreateCompound(COSMIC_DATA_STRING).getString(ARMOR_CRYSTAL).equals("null");
    }

    public static boolean isCrystalExtractor(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.GHAST_TEAR) return false;
        NBTItem item = new NBTItem(itemStack);
        return item.getOrCreateCompound(COSMIC_DATA_STRING).getString(CRYSTAL_EXTRACTOR) != null && !item.getOrCreateCompound(COSMIC_DATA_STRING).getString(CRYSTAL_EXTRACTOR).equals("null");
    }

    public static void refreshArmorSets(Player player) {
        CachedPlayer armorSetPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache) (armorSetPlayer.hasCache("armor_set") ? armorSetPlayer.getCache("armor_set") : armorSetPlayer.registerCache(new ArmorSetCache(player)));
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack item : armor) {
            if (ArmorSetAPI.hasSetTypeOnItem(item))
                cache.addArmorSet(ArmorSetAPI.getSetTypeOnItem(item));
        }
    }
}