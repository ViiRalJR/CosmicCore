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

public class Blacksmith extends WeaponDamageEventEnchant {

    @ConfigValue
    private double procChance = 0.06;

    public Blacksmith() {
        super("Blacksmith", EnchantTier.LEGENDARY, 5, EnchantType.AXE, "Chance to heal your most damaged piece of armor", "by 1-2 durability whenever you hit a player,", " but when it procs your attack will", "only deal 50% of the normal damage.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (Math.random() < procChance * enchantedItemBuilder.getEnchantmentLevel(this)) {
            for (ItemStack itemStack : attacker.getInventory().getArmorContents()) {
                if (!ItemUtils.isArmor(itemStack)) continue;
                itemStack.setDurability((short) (itemStack.getDurability() - 2));
            }
            super.getDamageHandler().reduceDamage(50, event, this.getName());
        }
    }
}