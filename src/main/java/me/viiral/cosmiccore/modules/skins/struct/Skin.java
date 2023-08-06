package me.viiral.cosmiccore.modules.skins.struct;

import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.DamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;


@Getter
public abstract class Skin implements Listener {

    private final String name;
    private final String applicable;
    private final DamageHandler damageHandler;

    public Skin(String name, String applicable) {
        this.name = name;
        this.applicable = applicable;
        this.damageHandler = CosmicCore.getInstance().getDamageHandler();
    }

    public String getID() {
        return this.getSkinIDFromName(this.name);
    }

    public String getApplicable() {
        return applicable;
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

    private String getSkinIDFromName(String name) {
        return name.replace(" ", "").toLowerCase();
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {}

    public void onAttacked(Player attacked, Entity attacker, EntityDamageByEntityEvent event) {}

    public abstract String getColor();

    public abstract List<String> getLore();

}
