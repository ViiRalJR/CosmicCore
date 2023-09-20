package me.viiral.cosmiccore.modules.skins;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.mask.struct.cache.MaskCache;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.modules.skins.struct.cache.SkinCache;
import me.viiral.cosmiccore.utils.cache.CachedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkinsAPI {
    public static final String SKIN_TYPE_TAG = "skinType";
    public static final String SKIN_TYPE_SEPARATOR = "@;;";
    public static final String SKIN_CACHE_TAG = "skins";
    public static final Material SKIN_MATERIAL = Material.EYE_OF_ENDER;


    public static List<Skin> getSkinTypesOnItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return Collections.emptyList();

        NBTItem wrapped = new NBTItem(itemStack);
        NBTCompound cosmicData = wrapped.getOrCreateCompound("cosmicData");

        return Optional.ofNullable(cosmicData.getString(SKIN_TYPE_TAG))
                .map(skinTypeStr -> Arrays.stream(skinTypeStr.split(SKIN_TYPE_SEPARATOR))
                        .filter(SkinRegister.getInstance()::isSkinFromId)
                        .map(SkinRegister.getInstance()::getSkinFromID)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public static boolean hasSkinOnItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;

        NBTItem wrapped = new NBTItem(item);
        NBTCompound compound = wrapped.getOrCreateCompound("cosmicData");

        return compound.hasTag(SKIN_TYPE_TAG);
    }

    public static boolean isSkin(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;

        NBTItem wrapped = new NBTItem(itemStack);
        NBTCompound compound = wrapped.getOrCreateCompound("cosmicData");

        if (itemStack.getType() != Material.EYE_OF_ENDER) return false;

        return Optional.ofNullable(compound.getString(SKIN_TYPE_TAG)).isPresent();
    }

    public static void refreshSkin(Player player) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        SkinCache skinCache = (SkinCache) Optional.ofNullable(cachedPlayer.getCache(SKIN_CACHE_TAG))
                .orElseGet(() -> cachedPlayer.registerCache(new SkinCache(player)));

        ItemStack[] armor = player.getInventory().getArmorContents();
        skinCache.clear();
        for (ItemStack itemStack : armor) {
            if (SkinsAPI.hasSkinOnItem(itemStack))
                for (Skin skin : SkinsAPI.getSkinTypesOnItem(itemStack)) {
                    skinCache.addSkin(skin);
                }
        }
    }

    public static boolean hasSkinOn(Player player, Skin skin) {
        CachedPlayer cachedPlayer = CosmicCore.getInstance().getCacheManager().getCachedPlayer(player);
        SkinCache skinCache = (SkinCache) cachedPlayer.getCache("skins");

        if (skinCache == null) refreshSkin(player);
        skinCache = (SkinCache) cachedPlayer.getCache("skins");

        return skinCache.getSkins().stream().anyMatch(m -> m.equals(skin));
    }
}
