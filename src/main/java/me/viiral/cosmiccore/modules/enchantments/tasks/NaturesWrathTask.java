package me.viiral.cosmiccore.modules.enchantments.tasks;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.NaturesWrathCache;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.CacheUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NaturesWrathTask extends BukkitRunnable {

    private final List<Player> affectedPlayers;
    private final int level;
    private int timeLeft;

    public NaturesWrathTask(List<Player> affectedPlayers, int level) {
        this.affectedPlayers = affectedPlayers;
        this.level = level;
        this.timeLeft = 5;
    }

    @Override
    public void run() {
        if (this.timeLeft <= 0) {
            for (Player victim : this.affectedPlayers) {
                if (victim != null) {
                    NaturesWrathCache naturesWrathCache = CacheUtils.getNaturesWrathCache(victim);
                    naturesWrathCache.removeNaturesWrathEffect(victim);
                }
            }
            this.cancel();
            return;
        }

        for (Player victim : this.affectedPlayers) {
            if (victim != null && !victim.isDead()) {

                if (!CosmicCore.getInstance().getWorldGuardUtils().canPvPInRegion(victim)) {
                    continue;
                }

                this.applyNaturesWrathDamage(victim);
            }
        }
        this.timeLeft--;
    }

    public void applyNaturesWrathDamage(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
        player.damage(this.level);
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 2.0f, 2.0f);
        player.sendMessage(CC.translate("&2&l** NATURES **"));
    }
}

