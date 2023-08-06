package me.viiral.cosmiccore.modules.enchantments.enchants.soul;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class Paradox extends ArmorIncomingPVPDamageEventEnchant implements SoulEnchant {

    @ConfigValue
    private double procChance = 0.06;
    @ConfigValue
    private int cooldown = 80;
    @ConfigValue
    private String message = "&2&l** PARADOX [{healer}] (+{amount}HP) **]";
    private final SoulManager soulManager;

    private ParticleBuilder particle1;
    private ParticleBuilder particle2;
    private ParticleBuilder particle3;
    private ParticleBuilder particle4;
    private ParticleBuilder lavaParticle;

    public Paradox() {
        super("Paradox", EnchantTier.SOUL, false, 5, EnchantType.ARMOR, "Passive soul enchantment. Heals all nearby allies in a massive area", "around you for a portion of all damage dealt to you.");
        this.soulManager = CosmicCore.getInstance().getSoulManager();
        this.initParticles();
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (super.isOnCooldown(victim)) return;
        if (Math.random() < procChance) {
            SoulModeCache soulModeCache = soulManager.getSoulModeCache(victim);

            if (!soulModeCache.hasEnoughSouls(this.getSoulCost())) {
                this.lavaParticle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayers(victim, 30));
                victim.playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.7f, 0.4f);
                EnchantLanguage.OUT_OF_SOULS.send(victim);
                super.registerCooldown(victim, cooldown);
                return;
            }

            int level = enchantInfo.getLevel();
            int radius = 8 + level * 4;
            double amountToHeal = event.getDamage() * (double)(level / 10);

            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);

            soulModeCache.getSoulGemBuilder().removeSouls(5);
            soulModeCache.updateSoulGem();

            for (Entity nearbyEntity : victim.getNearbyEntities(radius, radius, radius)) {
                if (nearbyEntity.equals(victim)) continue;
                if (!(nearbyEntity instanceof Player)) continue;
                Player nearbyPlayer = ((Player) nearbyEntity);
                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) continue;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) return;

                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);

                if (fPlayer.getRelationTo(fPlayerNearby).isAtMost(Relation.NEUTRAL)) continue;
                this.particle1.setLocation(nearbyPlayer.getLocation()).display(PVPUtils.getNearbyPlayers(nearbyPlayer, 30));
                this.particle2.setLocation(nearbyPlayer.getLocation()).display(PVPUtils.getNearbyPlayers(nearbyPlayer, 30));
                this.particle3.setLocation(nearbyPlayer.getLocation()).display(PVPUtils.getNearbyPlayers(nearbyPlayer, 30));
                this.particle4.setLocation(nearbyPlayer.getLocation()).display(PVPUtils.getNearbyPlayers(nearbyPlayer, 30));
                super.sendMessage(nearbyPlayer, message, str -> str.replace("{healer}", victim.getName()).replace("{amount}", String.valueOf(amountToHeal)));
                super.getDamageHandler().healEntity(nearbyPlayer, amountToHeal, this.getName());
            }
            super.registerCooldown(victim, cooldown);
        }
    }

    @Override
    public int getSoulCost() {
        return 5;
    }

    private void initParticles() {
        this.particle1 = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY)
                .setAmount(20)
                .setOffsetY(1.5f)
                .setOffsetX(0.8f)
                .setOffsetZ(0.8f)
                .setSpeed(0.4f);
        this.particle2 = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY)
                .setAmount(20)
                .setOffsetY(1.5f)
                .setOffsetX(-0.8f)
                .setOffsetZ(0.8f)
                .setSpeed(0.4f);
        this.particle3 = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY)
                .setAmount(20)
                .setOffsetY(1.5f)
                .setOffsetX(0.8f)
                .setOffsetZ(-0.8f)
                .setSpeed(0.4f);
        this.particle4 = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY)
                .setAmount(20)
                .setOffsetY(1.5f)
                .setOffsetX(-0.8f)
                .setOffsetZ(-0.8f)
                .setSpeed(0.4f);
        this.lavaParticle = new ParticleBuilder(ParticleEffect.LAVA)
                .setAmount(30)
                .setOffsetY(0.5f)
                .setSpeed(0.15f);
    }
}
