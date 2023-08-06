package me.viiral.cosmiccore.modules.enchantments.struct.cache;

import lombok.Getter;
import lombok.Setter;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.struct.items.SoulGemBuilder;
import org.bukkit.entity.Player;

@Getter
public class SoulModeCache {

    @Setter
    private int slot;
    private final SoulGemBuilder soulGemBuilder;
    private final Player player;

    public SoulModeCache(Player player, SoulGemBuilder soulGemBuilder, int slot) {
        this.soulGemBuilder = soulGemBuilder;
        this.slot = slot;
        this.player = player;
    }

    public int getSouls() {
        return this.soulGemBuilder.getSouls();
    }

    public boolean hasEnoughSouls(int soulCost) {
        return this.getSouls() >= soulCost;
    }

    public void updateSoulGem() {
        if (!EnchantsAPI.isSoulGem(player.getInventory().getItem(this.getSlot()))) return;
        player.getInventory().setItem(this.getSlot(), this.getSoulGemBuilder().build());
        player.updateInventory();
    }
}
