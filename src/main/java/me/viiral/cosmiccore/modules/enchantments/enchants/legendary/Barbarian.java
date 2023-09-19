package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Barbarian extends WeaponDamageEventEnchant {

    @ConfigValue
    private int damageBuff = 20;

    public Barbarian() {
        super("Barbarian", EnchantTier.LEGENDARY, 4, EnchantType.AXE, "Multiplies damage against players who are wielding an AXE", " at the time they are hit.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player playerVictim)) return;

        ItemStack heldItem = playerVictim.getInventory().getItemInMainHand();

        if (heldItem.getType() == Material.AIR) return;
        if (!ItemUtils.isAxe(heldItem)) return;

        super.getDamageHandler().increaseDamage(damageBuff, event, this.getName());
    }

}
