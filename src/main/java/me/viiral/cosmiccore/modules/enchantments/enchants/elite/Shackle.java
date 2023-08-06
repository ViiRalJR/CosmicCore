package me.viiral.cosmiccore.modules.enchantments.enchants.elite;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.WeaponDamageEventEnchant;

import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Shackle extends WeaponDamageEventEnchant {

    public Shackle() {
        super("Shackle", EnchantTier.ELITE, 3, EnchantType.WEAPON, "Prevents mobs spawned from mob spawners ", "from suffering from knockback from your attacks.");
    }

    @Override
    public void runEntityDamageByEntityEvent(EntityDamageByEntityEvent event, LivingEntity victim, Player attacker, EnchantedItemBuilder enchantedItemBuilder) {
        if (victim instanceof Player) return;
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> victim.setVelocity(new Vector()), 1L);
    }
}
