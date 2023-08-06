package me.viiral.cosmiccore.modules.enchantments.enchants.simple;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDeathEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.utils.items.SkullUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class Decapitation extends WeaponDeathEventEnchant {

    public Decapitation() {
        super("Decapitation", EnchantTier.SIMPLE, 3, EnchantType.AXE, "Victims have a chance of dropping their head on death.");
    }

    @Override
    public void runEntityDeathEvent(EntityDeathEvent event, Player killer, LivingEntity victim, EnchantedItemBuilder enchantedItemBuilder) {
        if (!(victim instanceof Player)) return;
        killer.getInventory().addItem(SkullUtils.getSkullFromPlayer(((Player) victim)));
    }
}
