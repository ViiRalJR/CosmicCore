package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Blacksmith extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.06;

    public Blacksmith() {
        super("Blacksmith", EnchantTier.LEGENDARY, 5, EnchantType.AXE, "Chance to heal your most damaged piece of armor", "by 1-2 durability whenever you hit a player,", " but when it procs your attack will", "only deal 50% of the normal damage.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        double randomChance = Math.random();
        int enchantmentLevel = enchantedItemBuilder.getEnchantmentLevel(this);

        if (randomChance < procChance * enchantmentLevel) {
            ItemStack[] armorContents = attacker.getInventory().getArmorContents();
            for (ItemStack itemStack : armorContents) {
                if (ItemUtils.isArmor(itemStack) && itemStack.getItemMeta() instanceof Damageable damageable) {
                    damageable.setDamage(damageable.getDamage() + 2);
                    itemStack.setItemMeta(damageable);
                }
            }
            attacker.getInventory().setArmorContents(armorContents);
            getDamageHandler().reduceDamage(50, event, getName());
        }
    }
}
