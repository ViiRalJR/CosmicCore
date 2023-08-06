package me.viiral.cosmiccore.utils;

import me.viiral.cosmiccore.modules.enchantments.struct.cache.SoulTrackerCache;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache.*;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import org.bukkit.entity.Player;

public final class CacheUtils {

    private CacheUtils() {

    }

    public static BleedStacksCache getBleedStackCache(Player player) {
        BleedStacksCache bleedStacksCache = (BleedStacksCache) CacheManager.getInstance().getCachedPlayer(player).getCache("bleed_stacks");

        if (bleedStacksCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new BleedStacksCache());

        return (BleedStacksCache) CacheManager.getInstance().getCachedPlayer(player).getCache("bleed_stacks");
    }

    public static RageStacksCache getRageStackCache(Player player) {
        RageStacksCache rageStacksCache = (RageStacksCache) CacheManager.getInstance().getCachedPlayer(player).getCache("rage_stacks");

        if (rageStacksCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new RageStacksCache());

        return (RageStacksCache) CacheManager.getInstance().getCachedPlayer(player).getCache("rage_stacks");
    }

    public static DiminishCache getDiminishLastAttackCache(Player player) {
        DiminishCache bleedStacksCache = (DiminishCache) CacheManager.getInstance().getCachedPlayer(player).getCache("diminish_last_damage");

        if (bleedStacksCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new DiminishCache());

        return (DiminishCache) CacheManager.getInstance().getCachedPlayer(player).getCache("diminish_last_damage");
    }

    public static SilenceCache getSilenceCache(Player player) {
        SilenceCache silenceCache = (SilenceCache) CacheManager.getInstance().getCachedPlayer(player).getCache("silence_effect");

        if (silenceCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new SilenceCache());

        return (SilenceCache) CacheManager.getInstance().getCachedPlayer(player).getCache("silence_effect");
    }

    public static DominateCache getDominateCache(Player player) {
        DominateCache dominateCache = (DominateCache) CacheManager.getInstance().getCachedPlayer(player).getCache("dominate_proc_time");

        if (dominateCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new DominateCache());

        return (DominateCache) CacheManager.getInstance().getCachedPlayer(player).getCache("dominate_proc_time");
    }

    public static TeleblockCache getTeleblockCache(Player player) {
        TeleblockCache teleblockCache = (TeleblockCache) CacheManager.getInstance().getCachedPlayer(player).getCache("teleblock");

        if (teleblockCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new TeleblockCache());

        return (TeleblockCache) CacheManager.getInstance().getCachedPlayer(player).getCache("teleblock");
    }

    public static NaturesWrathCache getNaturesWrathCache(Player player) {
        NaturesWrathCache naturesWrathCache = (NaturesWrathCache) CacheManager.getInstance().getCachedPlayer(player).getCache("natures-wrath");

        if (naturesWrathCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new NaturesWrathCache());

        return (NaturesWrathCache) CacheManager.getInstance().getCachedPlayer(player).getCache("natures-wrath");
    }

    public static DivineCache getDivineCache(Player player) {
        DivineCache divineCache = (DivineCache) CacheManager.getInstance().getCachedPlayer(player).getCache("divine_proc_time");

        if (divineCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new DivineCache());

        return (DivineCache) CacheManager.getInstance().getCachedPlayer(player).getCache("divine_proc_time");
    }

    public static AntiGankCache getAntiGankCache(Player player) {
        AntiGankCache antiGankCache = (AntiGankCache) CacheManager.getInstance().getCachedPlayer(player).getCache("anti_gank");

        if (antiGankCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new AntiGankCache());

        return (AntiGankCache) CacheManager.getInstance().getCachedPlayer(player).getCache("anti_gank");
    }

    public static AegisCache getAegisCache(Player player) {
        AegisCache aegisCache = (AegisCache) CacheManager.getInstance().getCachedPlayer(player).getCache("aegis");

        if (aegisCache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new AegisCache());

        return (AegisCache) CacheManager.getInstance().getCachedPlayer(player).getCache("aegis");
    }

    public static SoulTrackerCache getSoulTrackerCache(Player player) {
        SoulTrackerCache cache = (SoulTrackerCache) CacheManager.getInstance().getCachedPlayer(player).getCache("soul-tracker");

        if (cache == null) CacheManager.getInstance().getCachedPlayer(player).registerCache(new SoulTrackerCache());

        return (SoulTrackerCache) CacheManager.getInstance().getCachedPlayer(player).getCache("soul-tracker");
    }
}
