package me.viiral.cosmiccore.modules.user;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.ArmorSetAPI;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.skins.SkinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class User {

    private final UUID uuid;

    public final Map<PotionEffectType, PotionEffect> effects;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.effects = new HashMap<>();
    }

    public void addEffect(PotionEffectType type, int amplifier, int duration) {
        if (duration == 0) duration = PotionEffect.INFINITE_DURATION;
        if (!hasPotion(type)) addPotionEffect(type, amplifier, duration);
        if (isStronger(type, amplifier)) {
            removePotionEffect(type);
            addPotionEffect(type, amplifier, duration);
        }
    }

    public void clearEffects() {
        getPlayer().getActivePotionEffects().forEach(effect -> removePotionEffect(effect.getType()));
    }

    public void removePotionEffect(PotionEffectType type) {
        this.effects.remove(type);
        getPlayer().removePotionEffect(type);
        refresh();
    }


    private void addPotionEffect(PotionEffectType type, int amplifier, int duration) {
        PotionEffect effect = new PotionEffect(type, duration, amplifier);
        this.effects.put(type, effect);
        getPlayer().addPotionEffect(effect);
        if (duration != PotionEffect.INFINITE_DURATION) removeTask(type, duration);
    }


    private boolean hasPotion(PotionEffectType type) {
        return this.effects.containsKey(type);
    }


    private boolean isStronger(PotionEffectType type, int amplifier) {
        return this.effects.get(type).getAmplifier() < amplifier;
    }

    private void removeTask(PotionEffectType type, int duration) {
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () -> removePotionEffect(type), 20L * duration);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid) != null ? Bukkit.getPlayer(uuid) : null;
    }

    private void refresh() {
        Bukkit.getScheduler().runTaskLater(CosmicCore.getInstance(), () ->  {
            EnchantsAPI.reprocEnchants(getPlayer());
            MaskAPI.refreshMask(getPlayer());
            SkinsAPI.refreshSkin(getPlayer());
            ArmorSetAPI.refreshArmorSets(getPlayer());
        }, 20L);
    }


}
