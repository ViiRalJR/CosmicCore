package me.viiral.cosmiccore.utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;

import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtils {

    private final WorldGuardPlugin worldGuard;

    public WorldGuardUtils() {
        this.worldGuard = this.getWorldGuard();
    }

    public boolean canPvPInRegion(Player player) {
        return this.canPvPInRegion(player.getLocation());
    }

    public boolean canPvPInRegion(Location location) {
        RegionManager regionManager = this.worldGuard.getRegionManager(location.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(location);
        return set.queryState(null, DefaultFlag.PVP) != StateFlag.State.DENY;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (!(plugin instanceof WorldGuardPlugin)) return null;
        return (WorldGuardPlugin) plugin;
    }
}