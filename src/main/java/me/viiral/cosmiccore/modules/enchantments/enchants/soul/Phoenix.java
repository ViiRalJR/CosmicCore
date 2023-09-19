package me.viiral.cosmiccore.modules.enchantments.enchants.soul;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.annotations.ConfigValue;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantInfo;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.ArmorIncomingPVPDamageEventEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.interfaces.SoulEnchant;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.modules.enchantments.utils.PVPUtils;

import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Phoenix extends ArmorIncomingPVPDamageEventEnchant implements SoulEnchant {

    @ConfigValue
    private int cooldown = 160;
    @ConfigValue
    private String titleMessage = "&c&l** PHOENIX **";
    @ConfigValue
    private String nearbyPlayersMessage = "&c&l*** PHOENIX SOUL (&7{player}, -{soul-cost} souls&c&l) ***";
    @ConfigValue
    private String subTitleMessage = "&7You have &n{souls-left}&7 souls left.";
    @ConfigValue
    private String outOfSoulsMessage = "&c&l** OUT OF SOULS **";
    @ConfigValue
    private List<String> procMessage = Arrays.asList("&r ", "&6&l***&r &nPHOENIX SOUL&r &6&l***", "&c&l- {soul-cost} Soul Gems", "&7&n{souls-left}&7 souls left.", "&r ");
    private final SoulManager soulManager;
    private final ParticleBuilder particle;



    public Phoenix() {
        super("Phoenix", EnchantTier.SOUL, false, 3, EnchantType.BOOTS, "Passive soul enchantment. An attack that would normally kill", "you will instead heal you to full HP. Can only be activated", "once every couple minutes.");
        this.soulManager = CosmicCore.getInstance().getSoulManager();
        this.particle = new ParticleBuilder(ParticleEffect.FLAME)
                .setAmount(50)
                .setOffsetY(2)
                .setSpeed(0.1f);
    }

    @Override
    public void runIncomingDamageEvent(EntityDamageByEntityEvent event, Player victim, LivingEntity attacker, EnchantInfo enchantInfo) {
        if (victim.isDead()) return;
        if (victim.getHealth() <= 0) return;
        if (victim.getHealth() - event.getFinalDamage() > 0.0) return;
        if (super.isOnCooldown(victim)) return;

        int soulCost = this.getSoulCost();
        SoulModeCache soulModeCache = this.soulManager.getSoulModeCache(victim);

        if (!soulModeCache.hasEnoughSouls(soulCost)) {
            super.sendMessage(victim, this.outOfSoulsMessage);
            return;
        }

        if (MaskAPI.hasMaskOn((Player) attacker, MaskRegister.getInstance().getMaskFromName("Thanos"))) {
            attacker.sendMessage(CC.GoldB + "*** BLOCKED ENEMIES PHOENIX (THANOS MASK) ***");
            return;
        }

        if (MaskAPI.hasMaskOn((Player) attacker, MaskRegister.getInstance().getMaskFromName("Death Knight"))) {

            if ((new Random().nextInt(100) + 1) > 50) {
                attacker.sendMessage(CC.DarkAquaB + "*** BLOCKED ENEMIES PHOENIX (DEATH KNIGHT MASK) ***");
                return;
            }
        }

        soulModeCache.getSoulGemBuilder().removeSouls(soulCost);
        soulModeCache.updateSoulGem();
        super.getDamageHandler().healEntity(victim, victim.getMaxHealth(), this.getName());

        super.sendMessage(victim, this.procMessage, str -> str.replace("{soul-cost}", String.valueOf(soulCost)).replace("{souls-left}", String.valueOf(soulModeCache.getSouls())));
        victim.playSound(victim.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);

        for (Entity nearbyEntity : victim.getNearbyEntities(48.0, 48.0, 48.0)) {
            if (!(nearbyEntity instanceof Player)) continue;
            Player nearbyPlayer = (Player) nearbyEntity;
            super.sendMessage(nearbyPlayer, this.nearbyPlayersMessage, str -> str.replace("{player}", victim.getName()).replace("{soul-cost}", String.valueOf(soulCost)));
            nearbyPlayer.playSound(victim.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
        }

        this.particle.setLocation(victim.getLocation()).display(PVPUtils.getNearbyPlayers(victim, 30));

        super.registerCooldown(victim, this.cooldown);

    }

    @Override
    public int getSoulCost() {
        return ThreadLocalRandom.current().nextInt(500, 8000);
    }
}
