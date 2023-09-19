package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorEquipEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Implants extends ArmorEquipEventEnchant {

    public Implants() {
        super("Implants", EnchantTier.ULTIMATE, false, 3, EnchantType.HELMET, "Restores the wearer's health and", "hunger while moving.");
    }

    @EventHandler
    public void onFoodLoss(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!EnchantsAPI.hasEnchantment(player, this)) return;
        event.setCancelled(true);
        player.setFoodLevel(20);
        player.setSaturation((float) 5.0);
        super.getDamageHandler().healEntity(player, 1.0, this.getName());
    }

    @Override
    public void runArmorEquipEvent(Player player, int level) {
        player.setFoodLevel(20);
        player.setSaturation((float) 5.0);
    }

    @Override
    public void runArmorUnEquipEvent(Player player, int level) {

    }
}
