package me.viiral.cosmiccore.modules.armorsets.listeners;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.ArmorSetAPI;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorCrystalCache;
import me.viiral.cosmiccore.modules.armorsets.struct.cache.ArmorSetCache;
import me.viiral.cosmiccore.modules.armorsets.struct.items.ArmorCrystalBuilder;
import me.viiral.cosmiccore.modules.armorsets.struct.items.CrystalArmorBuilder;
import me.viiral.cosmiccore.modules.armorsets.struct.items.CrystalExtractorBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ArmorListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        ArmorSetAPI.refreshArmorSets(event.getPlayer());
        ArmorSetAPI.refreshArmorCrystals(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> ArmorSetAPI.refreshArmorSets(event.getEntity()), 30L);
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> ArmorSetAPI.refreshArmorCrystals(event.getEntity()), 30L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorChange(ArmorEquipEvent event) {
        ArmorSetCache cache = (ArmorSetCache) CacheManager.getInstance().getCachedPlayer(event.getPlayer()).getCache("armor_set");
        ArmorCrystalCache cache1 = (ArmorCrystalCache) CacheManager.getInstance().getCachedPlayer(event.getPlayer()).getCache("armor_crystal");
        if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) {
            if (ArmorSetAPI.hasSetTypeOnItem(event.getOldArmorPiece()))
                cache.removeArmorSet(ArmorSetAPI.getSetTypeOnItem(event.getOldArmorPiece()));
            if (ArmorSetAPI.hasCrystalTypeOnItem(event.getOldArmorPiece()))
                ArmorSetAPI.getCrystalTypeOnItem(event.getOldArmorPiece()).forEach(cache1::removeArmorCrystal);
        }
        if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR) {
            if (ArmorSetAPI.hasSetTypeOnItem(event.getNewArmorPiece()))
                cache.addArmorSet(ArmorSetAPI.getSetTypeOnItem(event.getNewArmorPiece()));
            if (ArmorSetAPI.hasCrystalTypeOnItem(event.getNewArmorPiece()))
                ArmorSetAPI.getCrystalTypeOnItem(event.getNewArmorPiece()).forEach(cache1::addArmorCrystal);

        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity attacked = event.getEntity();
        if (attacked instanceof Player) {
            ArmorSetCache cache = (ArmorSetCache) CacheManager.getInstance().getCachedPlayer((Player) attacked).getCache("armor_set");
            if (cache.getCurrentArmorSet() != null)
                cache.getCurrentArmorSet().onAttacked((Player) attacked, attacker, event);

            ArmorCrystalCache cache1 = (ArmorCrystalCache) CacheManager.getInstance().getCachedPlayer((Player) attacked).getCache("armor_crystal");
            if (cache1.hasCrystals())
                cache1.getCrystals().forEach((armorSet, amount) -> armorSet.onAttackedCrystal((Player) attacked, attacker, amount, event));
        }
        if (attacker instanceof Player) {
            ArmorSetCache cache = (ArmorSetCache) CacheManager.getInstance().getCachedPlayer((Player) attacker).getCache("armor_set");
            if (cache.getCurrentArmorSet() != null)
                cache.getCurrentArmorSet().onAttack((Player) attacker, attacked, event);

            if (attacked instanceof Player) {
                ArmorCrystalCache cache1 = (ArmorCrystalCache) CacheManager.getInstance().getCachedPlayer((Player) attacked).getCache("armor_crystal");
                if (cache1.hasCrystals())
                    cache1.getCrystals().forEach((armorSet, amount) -> armorSet.onAttackCrystal((Player) attacker, attacked, amount, event));
            }
        }
    }

    @EventHandler
    public void onCrystalRemove(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;
        if (!ArmorSetAPI.isCrystalExtractor(event.getCursor()) || !ItemUtils.isArmor(event.getCurrentItem())) return;

        CrystalArmorBuilder crystalArmorBuilder = new CrystalArmorBuilder(event.getCurrentItem());
        CrystalExtractorBuilder crystalExtractorBuilder = new CrystalExtractorBuilder(event.getCursor());

        CrystalResult result = removeCrystal(crystalArmorBuilder, crystalExtractorBuilder);

        switch (result) {
            case DOESNT_CONTAIN:
                event.setCancelled(true);
                player.sendMessage(CC.translate("&c&l(!)&c This armor piece does not contain an armor crystal."));
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 5.0f, 0.1F);
                break;
            case INVALID_COUNT:
                event.setCancelled(true);
                break;
            case SUCCESS:
                event.setCancelled(true);
                ItemStack crystal = new ArmorCrystalBuilder(crystalExtractorBuilder.getPercentage(), ArmorSetAPI.getCrystalTypeOnItem(event.getCurrentItem())).build();
                event.setCursor(crystal);
                event.setCurrentItem(crystalArmorBuilder.removeCrystal().build());
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 0.75F);
                break;
        }
    }

    @EventHandler
    public void onCrystalApply(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        if (!ArmorSetAPI.isArmorCrystal(event.getCursor()) || !ItemUtils.isArmor(event.getCurrentItem())) return;

        CrystalArmorBuilder crystalItemBuilder = new CrystalArmorBuilder(event.getCurrentItem());
        ArmorCrystalBuilder crystalBuilder = new ArmorCrystalBuilder(event.getCursor());

        CrystalResult result = applyCrystal(crystalItemBuilder, crystalBuilder);

        if (result == CrystalResult.CONTAINS) {
            event.setCancelled(true);
            player.sendMessage(CC.translate("&c&l(!)&c Cannot apply crystal, armor piece already contains a crystal or is an armor set piece."));
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 5.0F, 0.1F);
        } else if (result == CrystalResult.FAIL) {
            event.setCancelled(true);
            event.setCursor(null);
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 5.0F, 0.1F);
        } else if (result == CrystalResult.SUCCESS) {
            event.setCancelled(true);
            crystalItemBuilder.applyMultiCrystal(crystalBuilder.getArmorSets());
            event.setCurrentItem(crystalItemBuilder.build());
            event.setCursor(null);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 0.75f);
        } else if (result == CrystalResult.INVALID_COUNT) {
            event.setCancelled(true);
        }
    }

    private CrystalResult applyCrystal(CrystalArmorBuilder crystalItemBuilder, ArmorCrystalBuilder armorCrystalBuilder) {

        if (crystalItemBuilder.hasCrystal())
            return CrystalResult.CONTAINS;

        if (crystalItemBuilder.build().getAmount() > 1 || armorCrystalBuilder.build().getAmount() > 1)
            return CrystalResult.INVALID_COUNT;

        if (armorCrystalBuilder.getSuccessRate() > this.generateChance())
            return CrystalResult.SUCCESS;

        return CrystalResult.FAIL;
    }

    private CrystalResult removeCrystal(CrystalArmorBuilder crystalArmorBuilder, CrystalExtractorBuilder crystalExtractorBuilder) {

        if (!crystalArmorBuilder.hasCrystal())
            return CrystalResult.DOESNT_CONTAIN;

        if (crystalArmorBuilder.build().getAmount() > 1 || crystalExtractorBuilder.build().getAmount() > 1)
            return CrystalResult.INVALID_COUNT;

        return CrystalResult.SUCCESS;
    }

    private enum CrystalResult {
        SUCCESS, FAIL, CONTAINS, INVALID_COUNT, DOESNT_CONTAIN
    }

    private int generateChance() {
        Random random = new Random();
        return random.nextInt(101);
    }

}
