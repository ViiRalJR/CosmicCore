package me.viiral.cosmiccore.modules.enchantments.commands;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.items.EnchantedItemBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.items.SoulGemBuilder;
import me.viiral.cosmiccore.modules.enchantments.utils.ItemUtils;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Command("withdrawsouls")
public class WithdrawSoulsCommand  extends CommandBase {

    @Default
    public void mainCommand(Player sender, Integer amount) {
        if (!ItemUtils.isWeapon(sender.getItemInHand())) return;
        if (amount <= 0) return;

        if (!InventoryUtils.hasAvailableSlot(sender)) {
            EnchantLanguage.FULL_INVENTORY.send(sender);
            return;
        }

        EnchantedItemBuilder enchantedItemBuilder = new EnchantedItemBuilder(sender.getItemInHand());

        if (!enchantedItemBuilder.hasSoulTrackerAlreadyApplied()) {
            EnchantLanguage.DOESNT_HAVE_SOUL_TRACKER.send(sender);
            return;
        }

        if (enchantedItemBuilder.getHarvestedSouls() < amount) {
            EnchantLanguage.OUT_OF_SOULS.send(sender);
            sender.playSound(sender.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.7f, 0.4f);
            return;
        }

        enchantedItemBuilder.removeHarvestedSouls(amount);
        enchantedItemBuilder.updateSoulsHarvested();
        sender.setItemInHand(enchantedItemBuilder.build());

        sender.getInventory().addItem(new SoulGemBuilder(amount * enchantedItemBuilder.getSoulTrackerTier().getSoulValue()).build());
    }
}
