package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class ArrowDeflect extends Enchantment implements Reloadable {

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 100;
    @ConfigValue
    private double particleYoffset = 1.0;
    @ConfigValue
    private double particleSpeed = 0.1;

    public ArrowDeflect() {
        super("Arrow Deflect", EnchantTier.ULTIMATE, false, 4, EnchantType.ARMOR, "Prevents you from being damaged by enemy arrows", "more often than once every level x 400 milliseconds.");
    }

    @EventHandler
    public void onIncomingArrowDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow)) return;
        if (!(event.getEntity() instanceof Player victim)) return;

        if (!EnchantsAPI.hasEnchantment(victim, this)) return;
        if (super.isOnCooldown(victim)) return;
        event.setCancelled(true);
        super.registerCooldown(victim, 20);
        victim.playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.7f, 0.2f);
        this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.SPELL)
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }
}
