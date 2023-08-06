package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnDamageEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public abstract class ArmorPlayerDamageEventEnchant extends Enchantment {

    public ArmorPlayerDamageEventEnchant(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamage() <= 0) return;

        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (!EnchantsAPI.hasEnchantment(player, this)) return;

        EnchantInfo info = EnchantsAPI.getEnchantmentInfo(player, this);

        EnchantProcEvent procEvent = new EnchantProcOnDamageEvent(player, event.getCause(), this, info.getLevel());
        Bukkit.getPluginManager().callEvent(procEvent);

        if(procEvent.isCancelled()) return;

        this.runOnDamage(event, player, info);
    }

    public abstract void runOnDamage(EntityDamageEvent event, Player player, EnchantInfo enchantInfo);
}
