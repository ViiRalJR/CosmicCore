package me.viiral.cosmiccore.modules.enchantments.enchants.soul;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.NaturesWrathCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.modules.enchantments.tasks.NaturesWrathTask;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.modules.user.User;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.CacheUtils;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NaturesWrath extends ArmorIncomingPVPDamageEventEnchant implements SoulEnchant {

    //TODO: message

    @ConfigValue
    private double procChance = 0.02;
    @ConfigValue
    private int cooldown = 200;
    @ConfigValue
    private List<String> procMessage = Arrays.asList("&r ", "&c&l** NATURE'S WRATH **", "&c&l- 75 Soul Gems", "&7&n{souls-left}&7 souls left.", "&r ");

    private final SoulManager soulManager;

    private final ParticleBuilder spellParticle;
    private final ParticleBuilder explosionParticle;
    private final ParticleBuilder lavaParticle;

    public NaturesWrath() {
        super("Natures Wrath", EnchantTier.SOUL, false, 4, EnchantType.ARMOR, "Passive Soul Enchantment. Temporarily freeze all enemies in a massive area around you,", "pushing them back and dealing massive nature damage.");
        this.soulManager = CosmicCore.getInstance().getSoulManager();
        this.explosionParticle = new ParticleBuilder(ParticleEffect.EXPLOSION_LARGE)
                .setAmount(5)
                .setOffsetY(0.5f)
                .setSpeed(0.15f);
        this.spellParticle = new ParticleBuilder(ParticleEffect.SPELL)
                .setAmount(50)
                .setOffsetY(0.5f)
                .setSpeed(0.15f);

        this.lavaParticle = new ParticleBuilder(ParticleEffect.LAVA)
                .setAmount(30)
                .setOffsetY(0.5f)
                .setSpeed(0.15f);
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (!(attacker instanceof Player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) return;
        if (super.isOnCooldown(victim)) return;

        User user = CosmicCore.getInstance().getUserManager().getUsers().get(victim.getUniqueId());

        if (user != null && user.types.contains(EffectType.IMMUNE_TO_NATURES)) return;

        if (MaskAPI.hasMaskOn((Player) attacker, MaskRegister.getInstance().getMaskFromName("Thanos"))) {
            victim.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l*** THANOS MASK (&7Blocked Natures Wrath&b&l) ***"));
            return;
        }

        if (MaskAPI.hasMaskOn((Player) attacker, MaskRegister.getInstance().getMaskFromName("Zues"))) {
            victim.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l*** ZEUS MASK (&7Blocked Natures Wrath&b&l) ***"));
            return;
        }

        if (Math.random() < this.procChance) {
            SoulModeCache soulModeCache = this.soulManager.getSoulModeCache(victim);

            if (!soulModeCache.hasEnoughSouls(this.getSoulCost())) {
                this.lavaParticle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayers(victim, 30));
                victim.playSound(victim.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                EnchantLanguage.OUT_OF_SOULS.send(victim);
                super.registerCooldown(victim, this.cooldown);
                return;
            }

            soulModeCache.getSoulGemBuilder().removeSouls(this.getSoulCost());
            soulModeCache.updateSoulGem();

            super.sendMessage(victim, this.procMessage, str -> str.replace("{souls-left}", String.valueOf(soulModeCache.getSouls())));

            int level = enchantInfo.getLevel();
            int radius = 8 + level * 5;

            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(victim);
            List<Player> affectedPlayers = new ArrayList<>();

            for (Entity nearbyEntity : victim.getNearbyEntities(radius, radius, radius)) {
                if (!(nearbyEntity instanceof Player)) continue;
                Player nearbyPlayer = (Player) nearbyEntity;

                if (nearbyPlayer.getGameMode() != GameMode.SURVIVAL) continue;
                if (!PVPUtils.canPvPInRegion(nearbyPlayer)) continue;


                FPlayer fPlayerNearby = FPlayers.getInstance().getByPlayer(nearbyPlayer);

                if (fPlayer.getRelationTo(fPlayerNearby).isAtLeast(Relation.TRUCE)) continue;

                affectedPlayers.add(nearbyPlayer);
                this.spellParticle.setLocation(nearbyPlayer.getLocation()).display(PVPUtils.getNearbyPlayers(nearbyPlayer, 30));
                this.explosionParticle.setLocation(nearbyPlayer.getLocation()).display(PVPUtils.getNearbyPlayers(nearbyPlayer, 30));
                CacheUtils.getNaturesWrathCache(nearbyPlayer).applyNaturesWrathEffect(nearbyPlayer, level);
            }



            NaturesWrathTask naturesWrathTask = new NaturesWrathTask(affectedPlayers, level);
            naturesWrathTask.runTaskTimer(CosmicCore.getInstance(), 20L, 20L);

            super.registerCooldown(victim, this.cooldown);
        }
    }

    @EventHandler
    public void onPlayerLeaveWhileNatured(PlayerQuitEvent event) {
        NaturesWrathCache naturesWrathCache = CacheUtils.getNaturesWrathCache(event.getPlayer());
        if (naturesWrathCache.isAffected()) {
            naturesWrathCache.removeNaturesWrathEffect(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerDeathWhileNatured(PlayerDeathEvent event) {
        NaturesWrathCache naturesWrathCache = CacheUtils.getNaturesWrathCache(event.getEntity());
        if (naturesWrathCache.isAffected()) {
            naturesWrathCache.removeNaturesWrathEffect(event.getEntity());
        }
    }

    @Override
    public int getSoulCost() {
        return 75;
    }
}
