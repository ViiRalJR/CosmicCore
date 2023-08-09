package me.viiral.cosmiccore.modules.mask;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.modules.mask.struct.cache.MaskCache;
import me.viiral.cosmiccore.utils.cache.CachedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MaskAPI {
    public static final String MASK_TYPE_TAG = "maskType";
    public static final String MASK_TYPE_SEPARATOR = "@;;";
    public static final String MASK_CACHE_TAG = "mask";
    public static final Material MASK_MATERIAL = Material.PLAYER_HEAD;

    public static List<Mask> getMaskTypesOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return Collections.emptyList();
        }

        NBTItem wrapped = new NBTItem(item);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");

        return Optional.ofNullable(cosmicData.getString(MASK_TYPE_TAG))
                .map(maskTypeStr -> Arrays.stream(maskTypeStr.split(MASK_TYPE_SEPARATOR))
                        .filter(MaskRegister.getInstance()::isMaskFromId)
                        .map(MaskRegister.getInstance()::getMaskFromID)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public static boolean hasMaskOn(Player player, Mask mask) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        MaskCache maskCache = (MaskCache) cachedPlayer.getCache("mask");

        return maskCache.getMasks().stream().anyMatch(m -> m.equals(mask));
    }

    public static boolean hasMaskOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        NBTItem wrapped = new NBTItem(item);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");

        return cosmicData.hasTag(MASK_TYPE_TAG);
    }

    public static boolean isMask(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != MASK_MATERIAL) {
            return false;
        }

        NBTItem wrapped = new NBTItem(itemStack);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");

        return Optional.ofNullable(cosmicData.getString(MASK_TYPE_TAG)).isPresent();
    }

    public static void refreshMask(Player player) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        MaskCache maskCache = (MaskCache) Optional.ofNullable(cachedPlayer.getCache(MASK_CACHE_TAG))
                .orElseGet(() -> cachedPlayer.registerCache(new MaskCache(player)));

        ItemStack helmet = player.getInventory().getHelmet();
        if (hasMaskOnItem(helmet)) {
            getMaskTypesOnItem(helmet).forEach(maskCache::addMask);
        }
    }
}
