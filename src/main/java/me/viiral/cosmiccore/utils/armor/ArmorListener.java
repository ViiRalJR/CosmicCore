package me.viiral.cosmiccore.utils.armor;

import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ArmorListener implements Listener {

    private final List<String> blockedMaterials;

    public ArmorListener(){
        this.blockedMaterials = Arrays.asList(
                "FURNACE",
                "CHEST",
                "TRAPPED_CHEST",
                "BEACON",
                "DISPENSER",
                "DROPPER",
                "HOPPER",
                "WORKBENCH",
                "ENCHANTMENT_TABLE",
                "ENDER_CHEST",
                "ANVIL",
                "BED_BLOCK",
                "FENCE_GATE",
                "SPRUCE_FENCE_GATE",
                "BIRCH_FENCE_GATE",
                "ACACIA_FENCE_GATE",
                "JUNGLE_FENCE_GATE",
                "DARK_OAK_FENCE_GATE",
                "IRON_DOOR_BLOCK",
                "WOODEN_DOOR",
                "SPRUCE_DOOR",
                "BIRCH_DOOR",
                "JUNGLE_DOOR",
                "ACACIA_DOOR",
                "DARK_OAK_DOOR",
                "WOOD_BUTTON",
                "STONE_BUTTON",
                "TRAP_DOOR",
                "IRON_TRAPDOOR",
                "DIODE_BLOCK_OFF",
                "DIODE_BLOCK_ON",
                "REDSTONE_COMPARATOR_OFF",
                "REDSTONE_COMPARATOR_ON",
                "FENCE",
                "SPRUCE_FENCE",
                "BIRCH_FENCE",
                "JUNGLE_FENCE",
                "DARK_OAK_FENCE",
                "ACACIA_FENCE",
                "NETHER_FENCE",
                "BREWING_STAND",
                "CAULDRON",
                "LEGACY_SIGN_POST",
                "LEGACY_WALL_SIGN",
                "LEGACY_SIGN",
                "ACACIA_SIGN",
                "ACACIA_WALL_SIGN",
                "BIRCH_SIGN",
                "BIRCH_WALL_SIGN",
                "DARK_OAK_SIGN",
                "DARK_OAK_WALL_SIGN",
                "JUNGLE_SIGN",
                "JUNGLE_WALL_SIGN",
                "OAK_SIGN",
                "OAK_WALL_SIGN",
                "SPRUCE_SIGN",
                "SPRUCE_WALL_SIGN",
                "LEVER",
                "DAYLIGHT_DETECTOR_INVERTED",
                "DAYLIGHT_DETECTOR"
        );
    }

    @EventHandler(priority =  EventPriority.HIGHEST, ignoreCancelled = true)
    public final void inventoryClick(final InventoryClickEvent event){
        boolean shift = false, numberkey = false;
        if (event.isCancelled()) return;
        if (event.getAction() == InventoryAction.NOTHING) return;
        if (event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)){
            shift = true;
        }
        if (event.getClick().equals(ClickType.NUMBER_KEY)){
            numberkey = true;
        }
        if (event.getSlotType() != SlotType.ARMOR && event.getSlotType() != SlotType.QUICKBAR && event.getSlotType() != SlotType.CONTAINER) return;
        if (event.getClickedInventory() != null && !event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        if (!event.getInventory().getType().equals(InventoryType.CRAFTING) && !event.getInventory().getType().equals(InventoryType.PLAYER)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        ArmorType newArmorType = ArmorType.matchType(shift ? event.getCurrentItem() : event.getCursor());
        if (!shift && newArmorType != null && event.getRawSlot() != newArmorType.getSlot()){
            return;
        }
        if (shift){
            newArmorType = ArmorType.matchType(event.getCurrentItem());
            if (newArmorType != null){
                boolean equipping = event.getRawSlot() != newArmorType.getSlot();
                if (newArmorType.equals(ArmorType.HELMET) && (equipping == isAirOrNull(event.getWhoClicked().getInventory().getHelmet())) || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping ? isAirOrNull(event.getWhoClicked().getInventory().getChestplate()) : !isAirOrNull(event.getWhoClicked().getInventory().getChestplate())) || newArmorType.equals(ArmorType.LEGGINGS) && (equipping ? isAirOrNull(event.getWhoClicked().getInventory().getLeggings()) : !isAirOrNull(event.getWhoClicked().getInventory().getLeggings())) || newArmorType.equals(ArmorType.BOOTS) && (equipping ? isAirOrNull(event.getWhoClicked().getInventory().getBoots()) : !isAirOrNull(event.getWhoClicked().getInventory().getBoots()))){
                    if (InventoryUtils.hasAvailableSlot((Player) event.getWhoClicked())) {
                        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : event.getCurrentItem(), equipping ? event.getCurrentItem() : null);
                        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                        if (armorEquipEvent.isCancelled()) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = event.getCursor();
            ItemStack oldArmorPiece = event.getCurrentItem();
            if (numberkey){
                if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                    ItemStack hotbarItem = event.getClickedInventory().getItem(event.getHotbarButton());
                    if (!isAirOrNull(hotbarItem)){
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = event.getClickedInventory().getItem(event.getSlot());
                    } else {
                        newArmorType = ArmorType.matchType(!isAirOrNull(event.getCurrentItem()) ? event.getCurrentItem() : event.getCursor());
                    }
                }
            } else {
                if (isAirOrNull(event.getCursor()) && !isAirOrNull(event.getCurrentItem())){
                    newArmorType = ArmorType.matchType(event.getCurrentItem());
                }
            }
            if (newArmorType != null && event.getRawSlot() == newArmorType.getSlot()){
                ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
                if (event.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                ArmorEquipEvent armorEquipEvent;

                if (event.getClick().equals(ClickType.DOUBLE_CLICK)) {
                    method = ArmorEquipEvent.EquipMethod.DOUBLE_PICK_DROP;
                    armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), method, newArmorType, newArmorPiece, oldArmorPiece);
                } else {
                    armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                }

                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

                if (armorEquipEvent.isCancelled()){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority =  EventPriority.LOW)
    public void playerInteractEvent(PlayerInteractEvent e){
        if (e.useItemInHand().equals(Result.DENY))return;
        if (e.getAction() == Action.PHYSICAL) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = e.getPlayer();
            if (!e.useInteractedBlock().equals(Result.DENY)){
                if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()){
                    Material mat = e.getClickedBlock().getType();
                    for(String s : this.blockedMaterials){
                        if (mat.name().equalsIgnoreCase(s)) return;
                    }
                }
            }
            ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null){
                if (newArmorType.equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet()) || newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate()) || newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings()) || newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots())){
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()){
                        e.setCancelled(true);
                        player.updateInventory();
                    }
                }
            }
        }
    }

    @EventHandler(priority =  EventPriority.HIGHEST, ignoreCancelled = true)
    public void inventoryDrag(InventoryDragEvent event){
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) return;
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)){
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), ArmorEquipEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()){
                event.setResult(Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e){
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null){
            Player p = e.getPlayer();
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()){
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short) (i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)){
                    p.getInventory().setHelmet(i);
                }else if (type.equals(ArmorType.CHESTPLATE)){
                    p.getInventory().setChestplate(i);
                }else if (type.equals(ArmorType.LEGGINGS)){
                    p.getInventory().setLeggings(i);
                }else if (type.equals(ArmorType.BOOTS)){
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e){
        Player p = e.getEntity();
        if (e.getKeepInventory()) return;
        for(ItemStack i : p.getInventory().getArmorContents()){
            if (!isAirOrNull(i)){
                Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i, null));
                // No way to cancel a death event.
            }
        }
    }

    public static boolean isAirOrNull(ItemStack item){
        return item == null || item.getType().equals(Material.AIR);
    }
}
