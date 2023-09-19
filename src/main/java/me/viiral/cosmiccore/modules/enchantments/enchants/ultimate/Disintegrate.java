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

public class Disintegrate extends WeaponDamageEventEnchant {

    public Disintegrate() {
        super("Disintegrate", EnchantTier.ULTIMATE, 4, EnchantType.SWORD, "Chance to deal double durability damage to all enemy armor", "with every attack.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        Player playerVictim = ((Player) victim);

        for (ItemStack itemStack : playerVictim.getInventory().getArmorContents()) {
            if (!ItemUtils.isArmor(itemStack)) continue;
            itemStack.setDurability((short) (itemStack.getDurability() + 1));
        }

    }
}