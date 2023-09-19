package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EnderWalker extends Enchantment {

    public EnderWalker() {
        super("Ender Walker", EnchantTier.ULTIMATE, false, 5, EnchantType.BOOTS, "Wither and Poison do not injur and have a chance", "to heal at high levels. ");
    }

    @EventHandler
    public void onDamageByPoisonAndWither(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.WITHER && event.getCause() != EntityDamageEvent.DamageCause.POISON) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (!player.isValid()) return;
        if (!EnchantsAPI.hasEnchantment(player, this)) return;

        super.getDamageHandler().cancelDamage(event, this.getName());

        if (EnchantsAPI.getEnchantmentLevel(player, this) < 4) return;

        super.getDamageHandler().healEntity(player, 1, this.getName());
    }
}
