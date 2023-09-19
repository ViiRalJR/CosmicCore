package me.viiral.cosmiccore.modules.combat;

import me.viiral.cosmiccore.CosmicCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class CombatListener implements Listener {

    private final CombatManager combatManager;

    public CombatListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }


    // TODO: 18/09/2023 Shield Damage Reduction, Sword Blocking, Sword Sweep, Critical Hits, Player Collision

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        combatManager.setAttackDelay(player);
        combatManager.setAttackSpeed(player);

    }

    // DISABLE CHORUS FRUIT

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.CHORUS_FRUIT) return;
        Player player = event.getPlayer();
        new BukkitRunnable() {
            public void run() {
                int newFoodLevel = Math.min(player.getFoodLevel() + 2, 20);
                float newSaturation = Math.min(player.getSaturation() + 4, newFoodLevel);
                player.setFoodLevel(newFoodLevel);
                player.setSaturation(newSaturation);
            }
        }.runTaskLater(CosmicCore.getInstance(), 2L);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) event.setCancelled(true);
    }

    // DISABLE ELYTRA

    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        InventoryType type = event.getInventory().getType();
        if (type != InventoryType.CRAFTING && type != InventoryType.PLAYER) return;
        ItemStack cursor = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();
        ClickType clickType = event.getClick();
        PlayerInventory inv = player.getInventory();
        int slot = event.getSlot();
        if (clickType == ClickType.NUMBER_KEY && slot == 38 && combatManager.isElytra(inv.getItem(event.getHotbarButton())) || slot == 38 && combatManager.isElytra(cursor) || combatManager.isElytra(currentItem) && slot != 38 && event.isShiftClick() || clickType == ClickType.SWAP_OFFHAND && slot == 38 && combatManager.isElytra(inv.getItem(40))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
        if (combatManager.isElytra(event.getItem())) event.setUseItemInHand(Event.Result.DENY);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        ItemStack oldCursor = event.getOldCursor();
        if (event.getInventorySlots().contains(38) && combatManager.isElytra(oldCursor)) event.setCancelled(true);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        PlayerInventory inventory = player.getInventory();
        ItemStack chestplate = inventory.getChestplate();
        if (chestplate == null) return;
        if (!combatManager.isElytra(chestplate)) return;
        inventory.setChestplate(new ItemStack(Material.AIR));
        if (inventory.firstEmpty() != -1) inventory.addItem(chestplate);
        else world.dropItem(player.getLocation(), chestplate);
    }

    // OLD KNOCKBACK

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        combatManager.getPlayerKnockbackMap().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerVelocityEvent(PlayerVelocityEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (!combatManager.getPlayerKnockbackMap().containsKey(uuid)) return;
        event.setVelocity(combatManager.getPlayerKnockbackMap().get(uuid));
        combatManager.getPlayerKnockbackMap().remove(uuid);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        if (attribute == null) return;
        attribute.getModifiers().forEach(attribute::removeModifier);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity attacker)) return;
        if (!(event.getEntity() instanceof Player victim)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if (event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) > 0.0) return;
        Location attackerLocation = attacker.getLocation();
        Location victimLocation = victim.getLocation();
        double a = attackerLocation.getX() - victimLocation.getX();
        double b = attackerLocation.getZ() - victimLocation.getZ();
        while (a * a + b * b < 1.0E-4) {
            a = (Math.random() - Math.random()) * 0.01;
            b = (Math.random() - Math.random()) * 0.01;
        }
        double magnitude = Math.sqrt(a * a + b * b);
        Vector playerVelocity = victim.getVelocity();
        playerVelocity.setX(playerVelocity.getX() / 2.0 - a / magnitude * 0.4);
        playerVelocity.setY(playerVelocity.getY() / 2.0 + 0.4);
        playerVelocity.setZ(playerVelocity.getZ() / 2.0 - b / magnitude * 0.4);
        EntityEquipment equipment = attacker.getEquipment();
        if (equipment != null) {
            ItemStack heldItem = equipment.getItemInMainHand().getType() == Material.AIR ? equipment.getItemInOffHand() : equipment.getItemInMainHand();
            int bonusKnockback = heldItem.getEnchantmentLevel(Enchantment.KNOCKBACK);
            if (attackerLocation instanceof Player player && player.isSprinting()) {
                ++bonusKnockback;
            }
            if (playerVelocity.getY() > 0.4) {
                playerVelocity.setY(0.4);
            }
            if (bonusKnockback > 0) {
                playerVelocity.add(new Vector(-Math.sin(attacker.getLocation().getYaw() * (float)Math.PI / 180.0f) * (double)bonusKnockback * 0.4, 0.1, Math.cos(attacker.getLocation().getYaw() * (float)Math.PI / 180.0f) * (double)bonusKnockback * 0.4));
            }
        }
        UUID victimId = victim.getUniqueId();
        combatManager.getPlayerKnockbackMap().put(victimId, playerVelocity);
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> combatManager.getPlayerKnockbackMap().remove(victimId), 1L);
    }


}
