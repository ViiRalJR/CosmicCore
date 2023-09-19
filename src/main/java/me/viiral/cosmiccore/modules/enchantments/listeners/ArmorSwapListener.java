package me.viiral.cosmiccore.modules.enchantments.listeners;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.ArmorSetAPI;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorCrystalCache;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorSetCache;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.skins.SkinsAPI;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorSwapListener implements Listener {

    private final CosmicCore plugin;

    public ArmorSwapListener(CosmicCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArmorSwap(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!event.hasItem()) return;

        ItemStack itemInHand = event.getPlayer().getItemInHand();
        int itemInHandArmorSlot = getArmorSlot(itemInHand);

        if (itemInHandArmorSlot == -1) return;

        Player player = event.getPlayer();
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        ItemStack armorItem = armorContents[itemInHandArmorSlot];


        if (armorItem == null || armorItem.getType() == Material.AIR) return;

        EnchantsAPI.clearEnchantsFromCacheFromOneArmorItem(event.getPlayer(), armorItem);

        armorContents[itemInHandArmorSlot] = itemInHand;

        event.getPlayer().getInventory().setArmorContents(armorContents);
        EnchantsAPI.reprocEnchantsFromOneArmorItem(player, itemInHand);

        ArmorSetCache armorSetCache = (ArmorSetCache) CacheManager.getInstance().getCachedPlayer(event.getPlayer()).getCache("armor_set");
        armorSetCache.clear();
        ArmorSetAPI.refreshArmorSets(player);

        ArmorCrystalCache armorCrystalCache = (ArmorCrystalCache) CacheManager.getInstance().getCachedPlayer(event.getPlayer()).getCache("armor_crystal");
        armorCrystalCache.clear();
        ArmorSetAPI.refreshArmorCrystals(player);

        player.setItemInHand(armorItem);
        player.updateInventory();
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.2F);
    }

    private int getArmorSlot(ItemStack itemStack) {
        if (!ItemUtils.isArmor(itemStack)) return -1;

        if (itemStack.getType().name().endsWith("HELMET")) {
            return 3;
        }

        if (itemStack.getType().name().endsWith("CHESTPLATE")) {
            return 2;
        }

        if (itemStack.getType().name().endsWith("LEGGINGS")) {
            return 1;
        }

        if (itemStack.getType().name().endsWith("BOOTS")) {
            return 0;
        }
        return -1;
    }
}
