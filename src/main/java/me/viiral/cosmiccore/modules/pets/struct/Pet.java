package me.viiral.cosmiccore.modules.pets.struct;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.user.UserManager;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.DamageHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

@Getter
public abstract class Pet implements Listener {

    private final String name;
    private final DamageHandler damageHandler;
    private final UserManager userManager;

    // Seconds
    public final int cooldown;
    public final int duration;

    public Pet(String name) {
        this.name = name;
        this.damageHandler = CosmicCore.getInstance().getDamageHandler();
        this.userManager = CosmicCore.getInstance().getUserManager();
        this.cooldown = 180;
        this.duration = 10;
    }

    public String getID() {
        return this.getPetIDFromName(this.name);
    }

    protected void sendMessage(LivingEntity player, List<String> message, UnaryOperator<String> function) {
        for (String msg : message) {
            String formattedMessage = (function != null) ? function.apply(msg) : msg;
            player.sendMessage(CC.translate(formattedMessage));
        }
    }

    protected void sendMessage(LivingEntity player, String message, UnaryOperator<String> function) {
        sendMessage(player, Collections.singletonList(message), function);
    }

    protected void sendMessage(LivingEntity player, String message) {
        sendMessage(player, Collections.singletonList(message), null);
    }

    protected void sendMessage(LivingEntity player, List<String> message) {
        sendMessage(player, message, null);
    }

    private String getPetIDFromName(String name) {
        return name.replace(" ", "".toLowerCase());
    }

    public abstract String getColor();
    public abstract List<String> getLore();

    public abstract void onRightClick(Player player);


    protected void addPotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
        userManager.addPotionEffect(player.getUniqueId(), type, amplifier, duration);
    }

    protected void removePotionEffect(Player player, PotionEffectType effectType, int amplifier) {
        userManager.removePotionEffect(player.getUniqueId(), effectType, amplifier);
    }
}
