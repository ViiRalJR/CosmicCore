package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.utils.cache.Cache;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class AntiGankCache extends Cache {

    @Getter
    private long lastHitTime;
    private final Set<Player> playerHits;

    public AntiGankCache() {
        super("anti_gank");
        this.updateHitTime();
        playerHits = new HashSet<>();
    }

    public void updateHitTime() {
        this.lastHitTime = System.currentTimeMillis();
    }

    public boolean isStillActive() {
        return lastHitTime + 20000L > System.currentTimeMillis();
    }

    public void addPlayerToList(Player player) {
        if (playerHits.contains(player)) return;
        this.playerHits.add(player);
    }

    public void clearPlayers() {
        this.playerHits.clear();
    }

    public boolean isPlayerAlreadyInList(Player player) {
        return this.playerHits.contains(player);
    }

    public int getAmountOfPlayers() {
        return this.playerHits.size();
    }
}
