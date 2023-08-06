package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcEvent;
import me.viiral.cosmiccore.modules.enchantments.events.EnchantProcOnEntityEvent;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class ArmorOutgoingPVPDamageEventEnchant extends Enchantment {

    public ArmorOutgoingPVPDamageEventEnchant(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    @EventHandler
    public void outgoingDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamage() <= 0) return;

        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;

        LivingEntity victim = (LivingEntity) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if (!PVPUtils.canPVP(attacker, victim)) return;

        if (!EnchantsAPI.hasEnchantment(attacker, this)) return;

        EnchantInfo info = EnchantsAPI.getEnchantmentInfo(attacker, this);

        EnchantProcEvent procEvent = new EnchantProcOnEntityEvent(attacker, victim, this, info.getLevel());
        Bukkit.getPluginManager().callEvent(procEvent);

        if(procEvent.isCancelled()) return;

        this.runOutgoingDamageEvent(event, attacker, victim, info);
    }

    public abstract void runOutgoingDamageEvent(EntityDamageByEntityEvent event, Player attacker, LivingEntity victim, EnchantInfo enchantInfo);
}
