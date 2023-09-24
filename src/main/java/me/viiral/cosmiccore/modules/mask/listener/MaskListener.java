package me.viiral.cosmiccore.modules.mask.listener;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.EquippableMask;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.mask.struct.cache.MaskCache;
import me.viiral.cosmiccore.modules.mask.struct.item.MaskBuilder;
import me.viiral.cosmiccore.modules.mask.struct.item.MaskHelmetBuilder;
import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.item.SkinArmorBuilder;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.armor.ArmorEquipEvent;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MaskListener implements Listener {

    private static final String MASK_CACHE_TAG = "mask";
    private static final Material AIR_MATERIAL = Material.AIR;
    private static final long REFRESH_DELAY = 30L;

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        MaskAPI.refreshMask(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> MaskAPI.refreshMask(event.getEntity()), REFRESH_DELAY);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorChange(ArmorEquipEvent event) {
        MaskCache cache = getMaskCacheFromPlayer(event.getPlayer());
        processArmorPiece(event.getOldArmorPiece(), cache::removeMask);
        processArmorPiece(event.getNewArmorPiece(), cache::addMask);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity attacked = event.getEntity();

        if (attacked instanceof Player) {
            MaskCache cache = getMaskCacheFromPlayer((Player) attacked);
            if (cache.hasMask()) {
                for (Mask mask : cache.getMasks()) {
                    mask.onAttacked((Player) attacked, attacker, event);
                }
            }
        }

        if (attacker instanceof Player) {
            MaskCache cache = getMaskCacheFromPlayer((Player) attacker);
            if (cache.hasMask()) {
                for (Mask mask : cache.getMasks()) {
                    mask.onAttack((Player) attacker, attacked, event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void armorEquipEvent(ArmorEquipEvent event) {
        ItemStack itemStack = event.getNewArmorPiece();

        if (!ItemUtils.isHelmet(itemStack)) return;

        MaskHelmetBuilder maskHelmetBuilder = new MaskHelmetBuilder(itemStack);

        if (!maskHelmetBuilder.hasMask()) return;

        maskHelmetBuilder.getMasks().forEach(mask -> {
            if (mask instanceof EquippableMask) {
                ((EquippableMask) mask).onEquip(event.getPlayer());
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void armorUnEquipEvent(ArmorEquipEvent event) {
        ItemStack itemStack = event.getOldArmorPiece();

        if (!ItemUtils.isHelmet(itemStack)) return;

        MaskHelmetBuilder maskHelmetBuilder = new MaskHelmetBuilder(itemStack);

        if (!maskHelmetBuilder.hasMask()) return;

        maskHelmetBuilder.getMasks().forEach(mask -> {
            if (mask instanceof EquippableMask) {
                ((EquippableMask) mask).onUnequip(event.getPlayer());
            }
        });
    }

    @EventHandler
    public void onMaskApply(InventoryClickEvent event) {
        handleMaskApplication(event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMaskRemove(InventoryClickEvent event) {
        handleMaskRemoval(event);
    }

    private MaskCache getMaskCacheFromPlayer(Player player) {
        return (MaskCache) CacheManager.getInstance().getCachedPlayer(player).getCache(MASK_CACHE_TAG);
    }

    private void processArmorPiece(ItemStack armorPiece, Consumer<Mask> maskAction) {
        if (isMaskOnNonAirItem(armorPiece)) {
            MaskAPI.getMaskTypesOnItem(armorPiece).forEach(maskAction);
        }
    }

    private void processPlayerEvent(Player player, BiConsumer<Mask, Player> maskAction, EntityDamageByEntityEvent event) {
        if (player != null) {
            MaskCache cache = getMaskCacheFromPlayer(player);
            if (cache.hasMask()) {
                for (Mask mask : cache.getMasks()) {
                    maskAction.accept(mask, player);
                }
            }
        }
    }

    private void handleMaskApplication(InventoryClickEvent event) {
        if (!isValidMaskApplicationEvent(event)) return;

        Player player = (Player) event.getWhoClicked();
        MaskHelmetBuilder item = new MaskHelmetBuilder(event.getCurrentItem());
        MaskBuilder mask = new MaskBuilder(event.getCursor());

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) return;

        MaskResult result = applyMask(item, mask);
        if (result == MaskResult.CONTAINS) {
            cancelEventWithMessage(event, player, "&c&l(!)&c Cannot apply mask, helmet already contains a mask.", Sound.ANVIL_LAND);
        } else if (result == MaskResult.SUCCESS) {
            cancelEventAndApplyMask(event, player, item, mask);
        }
    }

    private void handleMaskRemoval(InventoryClickEvent event) {
        if (!isValidMaskRemovalEvent(event)) return;

        Player player = (Player) event.getWhoClicked();
        MaskHelmetBuilder item = new MaskHelmetBuilder(event.getCurrentItem());

        if (removeMask(item) == MaskResult.SUCCESS) {
            cancelEventAndRemoveMask(event, player, item);
        }
    }

    private boolean isMaskOnNonAirItem(ItemStack item) {
        return item != null && item.getType() != AIR_MATERIAL && MaskAPI.hasMaskOnItem(item);
    }

    private boolean isValidMaskApplicationEvent(InventoryClickEvent event) {
        return event.getCurrentItem() != null && event.getCursor() != null
                && event.getWhoClicked().getGameMode() == GameMode.SURVIVAL
                && MaskAPI.isMask(event.getCursor()) && ItemUtils.isHelmet(event.getCurrentItem());
    }

    private boolean isValidMaskRemovalEvent(InventoryClickEvent event) {
        return event.getCurrentItem() != null && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                && event.getWhoClicked().getGameMode() == GameMode.SURVIVAL
                && ItemUtils.isHelmet(event.getCurrentItem()) && event.getClick().isRightClick();
    }

    private MaskResult applyMask(MaskHelmetBuilder item, MaskBuilder mask) {
        if (item.hasMask()) {
            return MaskResult.CONTAINS;
        } else if (item.build().getAmount() > 1 || mask.build().getAmount() > 1) {
            return MaskResult.INVALID_COUNT;
        } else {
            return MaskResult.SUCCESS;
        }
    }

    private MaskResult removeMask(MaskHelmetBuilder item) {
        return item.hasMask() ? MaskResult.SUCCESS : MaskResult.RETURN;
    }

    private void cancelEventWithMessage(InventoryClickEvent event, Player player, String message, Sound sound) {
        event.setCancelled(true);
        player.sendMessage(CC.translate(message));
        player.playSound(player.getLocation(), sound, 5.0F, 0.1F);
    }

    private void cancelEventAndApplyMask(InventoryClickEvent event, Player player, MaskHelmetBuilder item, MaskBuilder mask) {
        event.setCancelled(true);
        item.applyMask(mask.getMasks());
        event.setCurrentItem(item.build());
        event.setCursor(null);
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0f, 0.75f);
    }

    private void cancelEventAndRemoveMask(InventoryClickEvent event, Player player, MaskHelmetBuilder item) {
        event.setCancelled(true);
        event.setCursor(new MaskBuilder(item.getMasks()).build());
        event.setCurrentItem(item.removeMask().build());
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0f, 0.75f);
    }

    private enum MaskResult {
        SUCCESS, CONTAINS, INVALID_COUNT, RETURN
    }
}

