package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Greatsword extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.4;
    @ConfigValue
    private double damageBuff = 20;

    public Greatsword() {
        super("Greatsword", EnchantTier.ELITE, 5, EnchantType.SWORD, "Multiplies damage against players who are wielding a BOW", "at the time they are hit. ");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player playerVictim)) return;

        ItemStack mainHandItem = playerVictim.getInventory().getItemInMainHand();
        ItemStack offHandItem = playerVictim.getInventory().getItemInOffHand();

        // Checking both main hand and off hand for a bow
        if (mainHandItem.getType() != Material.BOW && offHandItem.getType() != Material.BOW) {
            return;
        }

        if (Math.random() < procChance) {
            super.getDamageHandler().increaseDamage(damageBuff, event, this.getName());
        }
    }
}
