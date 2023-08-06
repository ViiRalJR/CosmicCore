package me.viiral.cosmiccore.modules.enchantments.commands;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import me.viiral.cosmiccore.modules.enchantments.language.EnchantLanguage;
import me.viiral.cosmiccore.modules.enchantments.struct.items.SoulGemBuilder;
import me.viiral.cosmiccore.utils.player.InventoryUtils;
import org.bukkit.entity.Player;

@Command("splitsouls")
public class SplitSoulsCommand extends CommandBase {

    @Default
    public void mainCommand(Player sender, Integer amount) {
        if (!EnchantsAPI.isSoulGem(sender.getItemInHand())) return;
        if (amount <= 0) return;

        if (!InventoryUtils.hasAvailableSlot(sender)) {
            EnchantLanguage.FULL_INVENTORY.send(sender);
            return;
        }

        SoulGemBuilder soulGemBuilder = new SoulGemBuilder(sender.getItemInHand());

        if (soulGemBuilder.getSouls() <= amount) return;

        soulGemBuilder.removeSouls(amount);
        sender.setItemInHand(soulGemBuilder.build());
        sender.getInventory().addItem(new SoulGemBuilder(amount).build());
    }
}
