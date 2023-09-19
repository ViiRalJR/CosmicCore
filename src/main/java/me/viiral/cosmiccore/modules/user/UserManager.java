package me.viiral.cosmiccore.modules.user;

import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {

    private final Map<UUID, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public void createUser(UUID uuid) {
        this.users.put(uuid, new User(uuid));
    }

    public void removeUser(UUID uuid) {
        this.users.remove(uuid);
    }

    public void addPotionEffect(UUID uuid, PotionEffectType type, int amplifier, int duration) {
        this.users.get(uuid).addEffect(type, amplifier, duration);
    }

    public void removePotionEffect(UUID uuid, PotionEffectType type, int amplifier) {
        this.users.get(uuid).removePotionEffect(type, amplifier);
    }
}
