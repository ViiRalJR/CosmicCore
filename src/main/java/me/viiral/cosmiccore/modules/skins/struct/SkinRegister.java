package me.viiral.cosmiccore.modules.skins.struct;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.skins.skins.DriftaySkin;
import me.viiral.cosmiccore.modules.skins.skins.ViiRalSkin;
import me.viiral.cosmiccore.modules.skins.skins.amulet.*;
import org.bukkit.event.HandlerList;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class SkinRegister {

    @Getter
    public static SkinRegister instance;
    private final CosmicCore plugin;
    private final Map<String, Skin> skins;


    public SkinRegister(CosmicCore plugin) {
        this.plugin = plugin;
        instance = this;
        this.skins = new HashMap<>();
    }

    public void initialize() {
        registerAll(
                new AmuletOfCorruption(), new AmuletOfDestruction(), new GoldChain(), new LostAmulet(), new LuauLei(),
                new RadioactiveAmulet()

                // Secret Santa Next
        );
    }

    public void registerAll(Skin... skins) {
        Arrays.stream(skins).forEach(this::register);
    }

    public void register(Skin skin) {
        if (skin == null || skin.getID() == null || skin.getID().isEmpty()) {
            throw new IllegalArgumentException("Skin and skin ID must not be null or empty");
        }
        this.skins.put(skin.getID().toLowerCase(), skin);
        this.plugin.getServer().getPluginManager().registerEvents(skin, plugin);
    }

    public void unregisterAll() {
        this.skins.values().forEach(HandlerList::unregisterAll);
        this.skins.clear();
    }

    public Skin getSkinFromID(String id) {
        return Optional.ofNullable(id)
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase)
                .map(skins::get)
                .orElseThrow(() -> new IllegalArgumentException("Skin ID must not be null or empty."));
    }

    public String getSkinIDFromName(String name) {
        return Optional.ofNullable(name)
                .filter(Predicate.not(String::isEmpty))
                .map(n -> n.replace(" ", "").toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Skin name must not be null or empty."));
    }

    public Skin getSkinFromName(String name) {
        return this.skins.get(getSkinIDFromName(name));
    }

    public Collection<Skin> getSkins() {
        return Collections.unmodifiableCollection(this.skins.values());
    }

    public boolean isSkinFromId(String id) {
        return this.skins.containsKey(Optional.ofNullable(id)
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase)
                .orElseThrow(() -> new IllegalArgumentException("Mask ID Must not be null or empty.")));
    }

    public boolean isSkinFromName(String name) {
        return this.skins.containsKey(getSkinIDFromName(name));
    }

    public Skin selectRandomSkin() {
        List<Skin> skinList = new ArrayList<>(this.getSkins());
        return skinList.isEmpty() ? null : skinList.get(ThreadLocalRandom.current().nextInt(skinList.size()));
    }

}
