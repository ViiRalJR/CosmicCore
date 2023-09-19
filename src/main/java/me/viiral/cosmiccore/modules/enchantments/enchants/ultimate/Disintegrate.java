package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

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

public class Disintegrate extends WeaponDamageEventEnchant {

    public Disintegrate() {
        super("Disintegrate", EnchantTier.ULTIMATE, 4, EnchantType.SWORD, "Chance to deal double durability damage to all enemy armor", "with every attack.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player playerVictim)) return;

        ItemStack[] armorContents = playerVictim.getInventory().getArmorContents();
        if (armorContents == null) return;

        for (ItemStack itemStack : armorContents) {
            if (ItemUtils.isArmor(itemStack)) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta instanceof Damageable damageable) {
                    damageable.setDamage(damageable.getDamage() + 1);
                    itemStack.setItemMeta(damageable);
                }
            }
        }
    }
}
