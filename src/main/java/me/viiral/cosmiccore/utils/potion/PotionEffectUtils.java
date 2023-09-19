package me.viiral.cosmiccore.utils.potion;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PotionEffectUtils {

    private PotionEffectUtils() {

    }

    @Getter
    private static final Set<PotionEffectType> debuffsList = new HashSet<>(Arrays.asList(
            PotionEffectType.BLINDNESS,
            PotionEffectType.CONFUSION,
            PotionEffectType.HARM,
            PotionEffectType.SLOW,
            PotionEffectType.SLOW_DIGGING,
            PotionEffectType.WEAKNESS,
            PotionEffectType.WITHER,
            PotionEffectType.POISON
    ));

    public static void bless(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (getDebuffsList().contains(potionEffect.getType())) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }
}