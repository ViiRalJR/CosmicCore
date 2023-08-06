package me.viiral.cosmiccore.modules.enchantments.struct.souls;

import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulModeCache;
import me.viiral.cosmiccore.modules.enchantments.struct.items.SoulGemBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class SoulManager {

    private final Map<UUID, SoulModeCache> soulModePlayers;

    public SoulManager() {
        this.soulModePlayers = new HashMap<>();
    }

    public void enableSoulMode(Player player, SoulGemBuilder soulGemBuilder, int slot) {
        if (isInSoulMode(player)) return;
        soulModePlayers.put(player.getUniqueId(), new SoulModeCache(player, soulGemBuilder, slot));
        EnchantLanguage.SOUL_MODE_ENABLE.send(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.2F);
    }

    public void disableSoulMode(Player player) {
        if (!isInSoulMode(player)) return;
        soulModePlayers.remove(player.getUniqueId());
        EnchantLanguage.SOUL_MODE_DISABLE.send(player);
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.2F);
    }

    public void toggleSoulMode(Player player, SoulGemBuilder soulGemBuilder, int slot) {
        if (isInSoulMode(player)) {
            disableSoulMode(player);
            return;
        }
        enableSoulMode(player, soulGemBuilder, slot);
    }

    public boolean isInSoulMode(Player player) {
        return soulModePlayers.containsKey(player.getUniqueId());
    }

    public Collection<SoulModeCache> getPlayersInSoulMode() {
        return this.soulModePlayers.values();
    }

    public SoulModeCache getSoulModeCache(Player player) {
        return this.soulModePlayers.get(player.getUniqueId());
    }
}
