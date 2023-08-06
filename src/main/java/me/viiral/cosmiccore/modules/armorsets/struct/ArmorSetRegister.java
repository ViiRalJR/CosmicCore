package me.viiral.cosmiccore.modules.armorsets.struct;

import com.massivecraft.factions.shade.apache.Validate;
import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.armorsets.*;
import org.bukkit.Bukkit;

import java.util.*;

public class ArmorSetRegister {

    @Getter
    private static ArmorSetRegister instance;
    private final Map<String, ArmorSet> sets;

    public ArmorSetRegister() {
        instance = this;
        this.sets = new HashMap<>();
    }

    public void initialize() {
        this.registerAll(
                new KothArmorSet(), new PhantomArmorSet(), new TravelerArmorSet(), new YetiArmorSet(), new YijkiArmorSet(),
                new DragonArmorSet()
        );
    }

    public void registerAll(ArmorSet... armorSets) {
        for (ArmorSet set : armorSets) {
            this.register(set);
        }
    }

    public void register(ArmorSet set) {
        try {
            this.sets.put(set.getID(), set);
            CosmicCore.getInstance().getServer().getPluginManager().registerEvents(set, CosmicCore.getInstance());
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to register armor set " + set.getID());
            System.out.println(e.getMessage());
        }
    }

    public void unregisterAll() {
        this.sets.clear();
    }

    public void unregister(ArmorSet set) {
        this.sets.remove(set.getID());
    }

    public void reload() {
        this.unregisterAll();
        this.initialize();
    }

    public boolean isArmorSet(String name) {
        return (this.getArmorSetFromID(name) != null);
    }

    public ArmorSet getArmorSetFromID(String name) {
        Validate.notNull(name);
        Validate.notEmpty(name);
        return this.sets.get(name.toLowerCase());
    }

    public ArmorSet getArmorSetFromName(String name) {
        Validate.notNull(name);
        Validate.notEmpty(name);
        name = this.getEnchantIDFromName(name);
        return this.sets.get(name.toLowerCase());
    }

    public String getEnchantIDFromName(String name) {
        return name.replace(" ", "").toLowerCase(Locale.ROOT);
    }

    public Collection<ArmorSet> getArmorSets() {
        return Collections.unmodifiableCollection(this.sets.values());
    }
}
