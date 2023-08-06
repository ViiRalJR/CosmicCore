package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;

import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityDeathEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public abstract class ArmorPlayerDeathEventEnchant extends Enchantment {

    public ArmorPlayerDeathEventEnchant(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        if (victim.getKiller() == null) return;
        Player killer = victim.getKiller();

        if (!EnchantsAPI.hasEnchantment(victim, this)) return;

        EnchantInfo info = EnchantsAPI.getEnchantmentInfo(victim, this);

        EnchantProcEvent procEvent = new EnchantProcOnEntityDeathEvent(killer, victim, this, info.getLevel());
        Bukkit.getPluginManager().callEvent(procEvent);

        if(procEvent.isCancelled()) return;

        this.runEntityDeathEvent(event, killer, victim, info);
    }

    public abstract void runEntityDeathEvent(PlayerDeathEvent event, Player killer, Player victim, EnchantInfo enchantInfo);
}
