package me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.struct.cache.EnchantCooldownCache;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.DamageHandler;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import me.viiral.cosmiccore.utils.cache.CachedPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

@Getter
public abstract class Enchantment implements Listener {

    private final String name;
    private final List<String> description;
    private final EnchantTier tier;
    private final boolean stackable;
    private final int max;
    private final EnchantType type;
    private final DamageHandler damageHandler;
    private boolean isHeroic;

    public Enchantment(String name, EnchantTier tier, boolean stackable, int max, EnchantType type, String... description) {
        this.name = name;
        this.description = Arrays.asList(description);
        this.tier = tier;
        this.stackable = stackable;
        this.max = max;
        this.type = type;
        this.damageHandler = CosmicCore.getInstance().getDamageHandler();
        this.isHeroic = false;
    }

    public String getID() {
        return this.getName().replace(" ", "").toLowerCase(Locale.ROOT);
    }

    public void registerCooldown(Player player, long cooldown) {
        CachedPlayer cacheManager = CacheManager.getInstance().getCachedPlayer(player);

        cacheManager.registerCache(new EnchantCooldownCache(this, cooldown));
    }

    public boolean isOnCooldown(Player player) {
        CachedPlayer cacheManager = CacheManager.getInstance().getCachedPlayer(player);
        if (!cacheManager.hasCache("enchant_cooldown_" + this.getID())) return false;
        return ((EnchantCooldownCache) cacheManager.getCache("enchant_cooldown_" + this.getID())).isActive();
    }

    public int getEnchantTierWeight() {
        return this.getTier().getWeight();
    }

    protected void sendMessage(LivingEntity player, String message, UnaryOperator<String> function) {
        player.sendMessage(CC.translate(function.apply(message)));
    }

    protected void sendMessage(LivingEntity player, String message) {
        player.sendMessage(CC.translate(message));
    }

    protected void sendMessage(LivingEntity player, List<String> message) {
        message.stream()
                .map(CC::translate)
                .forEach(player::sendMessage);
    }
    protected void sendMessage(LivingEntity player, List<String> message, UnaryOperator<String> function) {
        message.stream()
                .map(function)
                .map(CC::translate)
                .forEach(player::sendMessage);
    }

    protected void setHeroic() {
        this.isHeroic = true;
    }
}

