package me.viiral.cosmiccore.modules.enchantments.enchants.unique;

import com.massivecraft.factions.*;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantType;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ObsidianDestroyer extends Enchantment {

    public ObsidianDestroyer() {
        super("Obsidian Destroyer", EnchantTier.UNIQUE, false, 5, EnchantType.PICKAXE, "Chance to automatically break obsidian", "on swing.");
    }

    @EventHandler
    public void procObsidianBreaker(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.hasBlock() && event.getClickedBlock().getType() == Material.OBSIDIAN) {

            ItemStack itemStack = event.getPlayer().getItemInHand();

            if (itemStack == null) return;
            if (!this.getType().getItems().contains(itemStack.getType())) return;

            EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(itemStack);

            if (!enchantedItemBuilder.hasEnchantment(this)) return;
            FLocation breakLoc = new FLocation(event.getClickedBlock().getLocation());
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(event.getPlayer());
            Faction faction = fPlayer.getFaction();

            if (faction.equals(Board.getInstance().getFactionAt(breakLoc)) || Board.getInstance().getFactionAt(breakLoc).isWilderness()) {
                event.getClickedBlock().setType(Material.AIR, true);
                event.getPlayer().getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.OBSIDIAN));
            }
        }
    }
}
