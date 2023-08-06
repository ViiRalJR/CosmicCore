package me.viiral.cosmiccore.modules.enchantments.listeners;


import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEquip;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.utils.armor.ArmorEquipEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantmentListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        EnchantsAPI.reprocEnchants(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        EnchantsAPI.clearEnchants(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void armorEquipEvent(ArmorEquipEvent event) {
        ItemStack itemStack = event.getNewArmorPiece();

        if (!ItemUtils.isEnchantable(itemStack)) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        enchantedItemBuilder.getEnchantments().forEach(((enchantment, level) -> {
            if (enchantment instanceof ArmorEquipEventEnchant) {
                EnchantProcEvent procEvent = new EnchantProcOnEquip(event.getPlayer(), enchantment, level);
                Bukkit.getPluginManager().callEvent(procEvent);
                if(procEvent.isCancelled()) return;
                ((ArmorEquipEventEnchant) enchantment).runArmorEquipEvent(event.getPlayer(), level);
            }
            EnchantsAPI.addEnchantmentToPlayer(event.getPlayer(), enchantment, level);
        }));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void armorUnEquipEvent(ArmorEquipEvent event) {
        ItemStack itemStack = event.getOldArmorPiece();

        if (!ItemUtils.isEnchantable(itemStack)) return;

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

        enchantedItemBuilder.getEnchantments().forEach(((enchantment, level) -> {
            if(enchantment instanceof ArmorEquipEventEnchant)
                ((ArmorEquipEventEnchant)enchantment).runArmorUnEquipEvent(event.getPlayer(), level);
            EnchantsAPI.removeEnchantmentFromPlayer(event.getPlayer(), enchantment, level);
        }));
    }
}
