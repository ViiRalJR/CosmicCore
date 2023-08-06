package me.viiral.cosmiccore.modules.mask.struct;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.mask.masks.DriftayMask;
import me.viiral.cosmiccore.modules.mask.masks.ViiralMask;
import org.bukkit.event.HandlerList;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class MaskRegister {

    @Getter
    public static MaskRegister instance;
    private final CosmicCore plugin;
    private final Map<String, Mask> masks;

    public MaskRegister(CosmicCore plugin) {
        this.plugin = plugin;
        instance = this;
        this.masks = new HashMap<>();
    }


    public void initialize() {
        registerAll(
                new DriftayMask(), new ViiralMask()
        );
    }

    public void registerAll(Mask... masks) {
        Arrays.stream(masks).forEach(this::register);
    }

    public void register(Mask mask) {
        if (mask == null || mask.getID() == null || mask.getID().isEmpty()) {
            throw new IllegalArgumentException("Mask and mask ID must not be null or empty.");
        }
        this.masks.put(mask.getID().toLowerCase(), mask);
        this.plugin.getServer().getPluginManager().registerEvents(mask, plugin);
    }

    public void unregisterAll() {
        this.masks.values().forEach(HandlerList::unregisterAll);
        this.masks.clear();
    }

    public Mask getMaskFromID(String id) {
        return Optional.ofNullable(id)
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase)
                .map(masks::get)
                .orElseThrow(() -> new IllegalArgumentException("Mask ID must not be null or empty."));
    }

    public String getMaskIDFromName(String name) {
        return Optional.ofNullable(name)
                .filter(Predicate.not(String::isEmpty))
                .map(n -> n.replace(" ", "").toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Mask name must not be null or empty."));
    }

    public Mask getMaskFromName(String name) {
        return this.masks.get(getMaskIDFromName(name));
    }


    public Collection<Mask> getMasks() {
        return Collections.unmodifiableCollection(this.masks.values());
    }

    public boolean isMaskFromId(String id) {
        return this.masks.containsKey(Optional.ofNullable(id)
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase)
                .orElseThrow(() -> new IllegalArgumentException("Mask ID must not be null or empty.")));
    }

    public boolean isMaskFromName(String name) {
        return this.masks.containsKey(getMaskIDFromName(name));
    }

    public Mask selectRandomMask() {
        List<Mask> maskList = new ArrayList<>(this.getMasks());
        return maskList.isEmpty() ? null : maskList.get(ThreadLocalRandom.current().nextInt(maskList.size()));
    }
}

