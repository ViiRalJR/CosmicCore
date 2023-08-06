package me.viiral.cosmiccore.modules.enchantments.enchants.legendary;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BloodLink extends Enchantment {

    public BloodLink() {
        super("Blood Link", EnchantTier.LEGENDARY, false, 5, EnchantType.ARMOR, "A chance to heal you for 1-2hp whenever", "your Guardians take damage.");
    }

    @EventHandler
    public void onDamageGuardian(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof IronGolem)) return;
        if (!(event.getDamager() instanceof Player)) return;

        Entity entity = event.getEntity();

        if (entity.isDead()) return;
        if (!entity.hasMetadata("guardian-owner")) return;

        Player golemOwner = Bukkit.getPlayer(String.valueOf(entity.getMetadata("guardian-owner")));

        if (golemOwner == null) return;

        if (EnchantsAPI.hasEnchantment(golemOwner, this)) {
            super.getDamageHandler().healEntity(golemOwner, 2, this.getName());
        }
    }
}
