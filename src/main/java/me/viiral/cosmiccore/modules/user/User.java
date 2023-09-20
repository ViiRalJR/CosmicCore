package me.viiral.cosmiccore.modules.user;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.ArmorSetAPI;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.mask.MaskAPI;
import me.viiral.cosmiccore.modules.skins.SkinsAPI;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.modules.skins.struct.cache.SkinCache;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
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
        if (duration == 0) duration = Integer.MAX_VALUE;
        if (!hasPotion(type)) addPotionEffect(type, amplifier, duration);

        if (type == PotionEffectType.SLOW) {
            if (SkinsAPI.hasSkinOn(getPlayer(), SkinRegister.getInstance().getSkinFromName("Snowflake Slippers"))) return;
            if (SkinsAPI.hasSkinOn(getPlayer(), SkinRegister.getInstance().getSkinFromName("Bloodstained Galoshes"))) return;
            if (SkinsAPI.hasSkinOn(getPlayer(), SkinRegister.getInstance().getSkinFromName("Hazmat Helmet"))) return;

        }

        if (PotionEffectUtils.getDebuffsList().contains(type)) {
            if (SkinsAPI.hasSkinOn(getPlayer(), SkinRegister.getInstance().getSkinFromName("Roller Skates"))) return;
            if (SkinsAPI.hasSkinOn(getPlayer(), SkinRegister.getInstance().getSkinFromName("Hazmat Boots"))) return;

        }

        if (isStronger(type, amplifier)) {
            removePotionEffect(type);
            addPotionEffect(type, amplifier, duration);
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


    private void addPotionEffect(PotionEffectType type, int amplifier, int duration) {
        PotionEffect effect = new PotionEffect(type, duration, amplifier);
        this.effects.put(type, effect);
        getPlayer().addPotionEffect(effect);
        if (duration != Integer.MAX_VALUE) removeTask(type, duration);
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
            SkinsAPI.refreshSkin(getPlayer());
            EnchantsAPI.reprocEnchants(getPlayer());
            MaskAPI.refreshMask(getPlayer());
            ArmorSetAPI.refreshArmorSets(getPlayer());
        }, 20L);
    }


}
