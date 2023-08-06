package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import me.viiral.cosmiccore.CosmicCore;
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

public abstract class ArmorIncomingPVPDamageEventEnchant extends Enchantment {

    public ArmorIncomingPVPDamageEventEnchant(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        super(name, tier, stackable, max, type, description);
    }

    @EventHandler
    public void incomingDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamage() == 0) return;

        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof LivingEntity)) return;

        Player victim = (Player) event.getEntity();
        LivingEntity attacker = (LivingEntity) event.getDamager();

        if (!PVPUtils.canPVP(victim, attacker)) return;

        if (!EnchantsAPI.hasEnchantment(victim, this)) return;

        EnchantInfo info = EnchantsAPI.getEnchantmentInfo(victim, this);

        EnchantProcEvent procEvent = new EnchantProcOnEntityEvent(attacker, victim, this, info.getLevel());
        Bukkit.getPluginManager().callEvent(procEvent);

        if(procEvent.isCancelled()) return;

        if (this.getTier() == EnchantTier.SOUL && !CosmicCore.getInstance().getSoulManager().isInSoulMode(victim)) return;

        this.runIncomingDamageEvent(event, victim, attacker, info);

    }

    public abstract void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo);
}
