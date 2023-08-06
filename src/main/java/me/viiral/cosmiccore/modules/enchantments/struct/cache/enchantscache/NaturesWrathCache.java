package me.viiral.cosmiccore.modules.enchantments.struct.cache.enchantscache;

import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.utils.cache.Cache;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NaturesWrathCache extends Cache {

    @Getter @Setter
    private boolean isAffected;

    public NaturesWrathCache() {
        super("natures-wrath");
        this.isAffected = false;
    }


    public void applyNaturesWrathEffect(Player player, int level) {
        player.setWalkSpeed(0.0f);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.SLOW);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (7 + level) * 20, 128));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (7 + level) * 20, 128));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (7 + level) * 20, 2));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2.0f, 2.0f);
        this.setAffected(true);
    }

    public void removeNaturesWrathEffect(Player player) {
        player.setWalkSpeed(0.2f);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.WEAKNESS);

        Enchantment springs = EnchantRegister.getInstance().getEnchantmentFromID("springs");
        if (EnchantsAPI.hasEnchantment(player, springs)) {
            int springsLevel = EnchantsAPI.getEnchantmentLevel(player, springs);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, springsLevel - 1), true);
        }

        Enchantment antigravity = EnchantRegister.getInstance().getEnchantmentFromID("antigravity");
        if (EnchantsAPI.hasEnchantment(player, antigravity)) {
            int antigravityLevel = EnchantsAPI.getEnchantmentLevel(player, antigravity);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, antigravityLevel + 2), true);
        }
        this.setAffected(false);
    }
}
