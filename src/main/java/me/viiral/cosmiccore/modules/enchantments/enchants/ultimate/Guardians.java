package me.viiral.cosmiccore.modules.enchantments.enchants.ultimate;

import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.Reloadable;import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class Guardians extends ArmorIncomingPVPDamageEventEnchant implements Reloadable {

    @ConfigValue
    private double procChance = 0.005;
    @ConfigValue
    private int cooldown = 200;

    private ParticleBuilder particle;

    @ConfigValue
    private int particleAmount = 50;
    @ConfigValue
    private double particleYoffset = 1.0;
    @ConfigValue
    private double particleSpeed = 0.1;

    public Guardians() {
        super("Guardians", EnchantTier.ULTIMATE, false, 10, EnchantType.ARMOR, "Chance to spawn iron golems to assist", "you in combat and watch over you.");
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;
        if (super.isOnCooldown(victim)) return;

        if (Math.random() < procChance * enchantInfo.getLevel()) {
            Player target = (Player) attacker;
//            Entity entity = EntityT.spawnEntity(new GuardiansIronGolem(victim.getWorld(), victim, target), victim.getLocation());
//            victim.playSound(victim.getLocation(), Sound.IRONGOLEM_DEATH, 1.0F, 0.55F);
//            this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayersExceptPlayer(victim, 30));
//            Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), entity::remove, 400L);
            super.registerCooldown(victim, cooldown);
        }
    }

    @EventHandler
    public void onEntityTargetPlayer(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() != EntityType.IRON_GOLEM) return;
        if (!(event.getTarget() instanceof Player)) return;
        if (!event.getEntity().hasMetadata("guardian-target")) return;
        String uuid = String.valueOf(event.getEntity().getMetadata("guardian-target").get(0).asString());
        if (!uuid.equals(event.getTarget().getUniqueId().toString())) event.setCancelled(true);
    }

    @Override
    public void reloadValues() {
        this.particle = new ParticleBuilder(ParticleEffect.SPELL_WITCH)
                .setAmount(particleAmount)
                .setOffsetY((float) particleYoffset)
                .setSpeed((float) particleSpeed);
    }
}
