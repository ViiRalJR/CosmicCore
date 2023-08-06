package me.viiral.cosmiccore.modules.enchantments.tasks;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AngelicTask extends BukkitRunnable {

    private final Player player;

    public AngelicTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (this.player == null || this.player.isDead()) {
            this.cancel();
            return;
        }

        if (!EnchantsAPI.hasEnchantment(player, "angelic")) {
            this.cancel();
            return;
        }

        CosmicCore.getInstance().getDamageHandler().healEntity(this.player, 2, "Angelic");
    }
}
