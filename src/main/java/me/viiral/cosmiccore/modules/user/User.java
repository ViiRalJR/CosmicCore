package me.viiral.cosmiccore.modules.user;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.ArmorSetAPI;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.skins.SkinsAPI;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.modules.user.effects.EffectType;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@Getter
public class User {

    private final UUID uuid;

    public final Map<PotionEffectType, PotionEffect> effects;
    public final List<EffectType> types;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.effects = new HashMap<>();
        this.types = new ArrayList<>();
    }

    public void addPotionEffect(PotionEffectType type, int amplifier, int duration) {
        if (type == PotionEffectType.SLOW && hasEffect(EffectType.IMMUNE_TO_SLOWNESS)) return;

        if (PotionEffectUtils.getDebuffsList().contains(type) && hasEffect(EffectType.AUTO_BLESS)) return;

        if (duration == 0) duration = Integer.MAX_VALUE;
        if (!hasPotion(type)) potionEffect(type, amplifier, duration);

        if (isStronger(type, amplifier)) {
            removePotionEffect(type);
            potionEffect(type, amplifier, duration);
        }
    }

    public void clearEffects() {
        getPlayer().getActivePotionEffects().forEach(effect -> removePotionEffect(effect.getType()));
    }

    public void removePotionEffect(PotionEffectType type) {
        if (this.effects.isEmpty()) return;
        this.effects.remove(type);
        getPlayer().removePotionEffect(type);
        refresh();
    }

    public void removePotionEffect(PotionEffectType type, int amplifier) {
        if (this.effects.isEmpty()) return;
        if (this.effects.get(type).getAmplifier() <= amplifier) {
            this.effects.remove(type);
            getPlayer().removePotionEffect(type);
            refresh();
        }
    }


    private void potionEffect(PotionEffectType type, int amplifier, int duration) {
        PotionEffect effect = new PotionEffect(type, duration, amplifier);
        this.effects.put(type, effect);
        getPlayer().addPotionEffect(effect);
        if (duration != Integer.MAX_VALUE) removeTask(type, duration);
    }


    private boolean hasPotion(PotionEffectType type) {
        return this.effects.containsKey(type);
    }

    private boolean hasEffect(EffectType type) {
        if (types.isEmpty()) return false;
        return types.contains(type);
    }

    public void addEffect(EffectType type) {
        this.types.add(type);
    }

    public void removeEffect(EffectType type) {
        this.types.remove(type);
        refresh();
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
            SkinsAPI.refreshSkin(getPlayer());
            EnchantsAPI.reprocEnchants(getPlayer());
            MaskAPI.refreshMask(getPlayer());
            ArmorSetAPI.refreshArmorSets(getPlayer());
        }, 10L);
    }


}
