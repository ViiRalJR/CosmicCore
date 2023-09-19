package me.viiral.cosmiccore.modules.pets.struct;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.pets.PetsAPI;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PetRegistry {

    @Getter
    public static PetRegistry instance;
    private final CosmicCore plugin;
    private final Map<String, Pet> pets;

    public PetRegistry(CosmicCore plugin) {
        this.plugin = plugin;
        instance = this;
        this.pets = new HashMap<>();
    }

    public void initialize() {

    }

    public void registerAll(Pet... pets) {

    }

    public void register(Pet pet) {
        if (pet == null || pet.getID() == null || pet.getID().isEmpty()) throw new IllegalArgumentException("Pet and pet ID must not be null or empty.");
        this.pets.put(pet.getID().toLowerCase(), pet);
        this.plugin.getServer().getPluginManager().registerEvents(pet, plugin);
    }

    public Pet getPetFromID(String id) {
        return Optional.ofNullable(id)
                .filter(n -> !n.isEmpty())
                .map(String::toLowerCase)
                .map(pets::get)
                .orElseThrow(() -> new IllegalArgumentException("Pet ID must not be null or empty"));
    }

    public String getPetIDFromName(String name) {
        return Optional.ofNullable(name)
                .filter(n -> !n.isEmpty())
                .map(n -> n.replace(" ", "").toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Pet name must not be null or empty"));
    }

    public Pet getPetFromName(String name) {
        return this.pets.get(getPetIDFromName(name));
    }

    public Collection<Pet> getPets() {
        return Collections.unmodifiableCollection(this.pets.values());
    }

    public boolean isPetFromId(String id) {
        return this.pets.containsKey(Optional.ofNullable(id)
                .filter(n -> !n.isEmpty())
                .map(String::toLowerCase)
                .orElseThrow(() -> new IllegalArgumentException("Pet ID must not null or empty.")));
    }

    public boolean isPetFromName(String name) {
        return this.pets.containsKey(getPetIDFromName(name));
    }

    public Pet selectRandomPet() {
        List<Pet> petList = new ArrayList<>(this.getPets());
        return petList.isEmpty() ? null : petList.get(ThreadLocalRandom.current().nextInt(petList.size()));
    }
}
