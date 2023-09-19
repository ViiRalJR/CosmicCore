package me.viiral.cosmiccore.modules.skins.listener;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEquip;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.skins.SkinsAPI;
import me.viiral.cosmiccore.modules.skins.struct.EquipableSkin;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.cache.SkinCache;
import me.viiral.cosmiccore.modules.skins.struct.item.SkinArmorBuilder;
import me.viiral.cosmiccore.modules.skins.struct.item.SkinBuilder;
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

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SkinListener implements Listener {

    private static final String SKIN_CACHE_TAG = "skins";
    private static final Material AIR_MATERIAL = Material.AIR;
    private static final long REFRESH_DELAY = 30L;

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        SkinsAPI.refreshSkin(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> SkinsAPI.refreshSkin(event.getEntity()), REFRESH_DELAY);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorChange(ArmorEquipEvent event) {
        SkinCache cache = getSkinCacheFromPlayer(event.getPlayer());
        processArmorPiece(event.getOldArmorPiece(), cache::removeSkin);
        processArmorPiece(event.getNewArmorPiece(), cache::addSkin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity attacked = event.getEntity();

        if (attacked instanceof Player) {
            SkinCache cache = getSkinCacheFromPlayer((Player) attacked);
            if (cache.hasSkin()) {
                for (Skin skin : cache.getSkins()) {
                    skin.onAttacked((Player) attacked, attacker, event);
                }
            }
        }
        if (attacker instanceof Player) {
            SkinCache cache = getSkinCacheFromPlayer((Player) attacker);
            if (cache.hasSkin()) {
                for (Skin skin : cache.getSkins()) {
                    skin.onAttack((Player) attacker, attacked, event);
                }
            }
        }
    }

    @EventHandler
    public void onSkinApply(InventoryClickEvent event) {
        handleSkinApplication(event);
    }

    @EventHandler
    public void onSkinRemove(InventoryClickEvent event) {
        handleSkinRemoval(event);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void armorEquipEvent(ArmorEquipEvent event) {
        ItemStack itemStack = event.getNewArmorPiece();

        if (!ItemUtils.isEnchantable(itemStack)) return;

        SkinArmorBuilder skinArmorBuilder = new SkinArmorBuilder(itemStack);

        if (!skinArmorBuilder.hasSkin()) return;

        skinArmorBuilder.getSkins().forEach(skin -> {
            if (skin instanceof EquipableSkin) {
                ((EquipableSkin) skin).onEquip(event.getPlayer());
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void armorUnEquipEvent(ArmorEquipEvent event) {
        ItemStack itemStack = event.getOldArmorPiece();

        if (!ItemUtils.isEnchantable(itemStack)) return;

        SkinArmorBuilder skinArmorBuilder = new SkinArmorBuilder(itemStack);

        if (!skinArmorBuilder.hasSkin()) return;

        skinArmorBuilder.getSkins().forEach(skin -> {
            if (skin instanceof EquipableSkin) {
                ((EquipableSkin) skin).onUnequip(event.getPlayer());
            }
        });
    }

    private SkinCache getSkinCacheFromPlayer(Player player) {
        return (SkinCache) CacheManager.getInstance().getCachedPlayer(player).getCache(SKIN_CACHE_TAG);
    }

    private void processArmorPiece(ItemStack armorPiece, Consumer<Skin> skinAction) {
        if (isSkinOnNonAirItem(armorPiece)) {
            SkinsAPI.getSkinTypesOnItem(armorPiece).forEach(skinAction);
        }
    }

    private void processPlayerEvent(Player player, BiConsumer<Skin, Player> skinAction, EntityDamageByEntityEvent event) {
        if (player != null) {
            SkinCache cache = getSkinCacheFromPlayer(player);
            if (cache.hasSkin()) {
                for (Skin skin : cache.getSkins()) {
                    skinAction.accept(skin, player);
                }
            }
        }
    }

    private void handleSkinApplication(InventoryClickEvent event) {
        if (!isValidSkinApplicationEvent(event)) return;

        Player player = (Player) event.getWhoClicked();
        SkinArmorBuilder item = new SkinArmorBuilder(event.getCurrentItem());
        SkinBuilder skin = new SkinBuilder(event.getCursor());

        SkinResult result = applySkin(item, skin);
        if (result == SkinResult.CONTAINS) {
            cancelEventWithMessage(event, player, "&c&l(!)&c Cannot apply skin, armor piece already contains a skin.", Sound.BLOCK_ANVIL_LAND);
        } else if (result == SkinResult.WRONG_ITEM) {
            cancelEventWithMessage(event, player, "&c&l(!)&c Cannot apply this skin to that type of armor piece.", Sound.BLOCK_ANVIL_LAND);
        } else if (result == SkinResult.SUCCESS) {
            cancelEventAndApplySkin(event, player, item, skin);
        }

    }

    private void handleSkinRemoval(InventoryClickEvent event) {
        if (!isValidSkinRemovalEvent(event)) return;

        Player player = (Player) event.getWhoClicked();
        SkinArmorBuilder item = new SkinArmorBuilder(event.getCurrentItem());

        if (removeSkin(item) == SkinResult.SUCCESS_REMOVAL) {
            cancelEventAndRemoveSkin(event, player, item);
        }
    }

    private boolean isSkinOnNonAirItem(ItemStack item) {
        return item != null && item.getType() != AIR_MATERIAL && SkinsAPI.hasSkinOnItem(item);
    }

    private boolean isValidSkinApplicationEvent(InventoryClickEvent event) {
        return event.getCurrentItem() != null && event.getCursor() != null
                && event.getWhoClicked().getGameMode() == GameMode.SURVIVAL
                && SkinsAPI.isSkin(event.getCursor()) && ItemUtils.isArmor(event.getCurrentItem())
                && event.getClick().isLeftClick();
    }

    private boolean isValidSkinRemovalEvent(InventoryClickEvent event) {
        return event.getCurrentItem() != null && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                && event.getWhoClicked().getGameMode() == GameMode.SURVIVAL
                && ItemUtils.isArmor(event.getCurrentItem()) && event.getClick().isRightClick() && event.getSlotType() != InventoryType.SlotType.ARMOR;
    }

    private SkinResult applySkin(SkinArmorBuilder armorItemBuilder, SkinBuilder skinBuilder) {
        ItemStack armorItem = armorItemBuilder.build();
        ItemStack skinItem = skinBuilder.build();

        if (armorItemBuilder.hasSkin()) {
            return SkinResult.CONTAINS;
        }

        if (armorItem.getAmount() > 1 || skinItem.getAmount() > 1) {
            return SkinResult.INVALID_COUNT;
        }

        List<Material> applicableMaterials = Arrays.asList(skinBuilder.getApplicable().getApplicable());

        if (!applicableMaterials.contains(armorItem.getType())) {
            return SkinResult.WRONG_ITEM;
        }

        return SkinResult.SUCCESS;
    }

    private SkinResult removeSkin(SkinArmorBuilder item) {
        if (item.hasSkin()) {
            if (!MaskAPI.hasMaskOnItem(item.build())) {
                return SkinResult.SUCCESS_REMOVAL;
            }
        }
        return SkinResult.RETURN;
    }

    private void cancelEventWithMessage(InventoryClickEvent event, Player player, String message, Sound sound) {
        event.setCancelled(true);
        player.sendMessage(CC.translate(message));
        player.playSound(player.getLocation(), sound, 5.0F, 0.1F);
    }

    private void cancelEventAndApplySkin(InventoryClickEvent event, Player player, SkinArmorBuilder item, SkinBuilder skin) {
        event.setCancelled(true);
        item.applySkin(skin.getSkins());
        event.setCurrentItem(item.build());
        event.setCursor(null);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 0.75f);
    }

    private void cancelEventAndRemoveSkin(InventoryClickEvent event, Player player, SkinArmorBuilder item) {
        event.setCancelled(true);
        event.setCursor(new SkinBuilder(item.getSkins()).build());
        event.setCurrentItem(item.removeSkin().build());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0F, 0.75f);
    }

    private enum SkinResult {
        SUCCESS_REMOVAL, SUCCESS, CONTAINS, INVALID_COUNT, RETURN, WRONG_ITEM
    }
}
