package me.viiral.cosmiccore.modules.armorsets.commands;

import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.items.ArmorCrystalBuilder;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@Command("armorcrystal")
public class AdminArmorCrystalCommand extends CommandBase {

    @Default
    @Permission("cosmic.admin")
    public void mainCommand(CommandSender sender)  {
        sender.sendMessage(CC.translate("&c&l(!)&c /armorcrystal give <player> <type>"));
    }

    @SubCommand("multi")
    @WrongUsage("&cUsage: /armorcrystal multi <player> <percentage> <armorset...>")
    @Permission("cosmic.admin")
    public void multiCommand(CommandSender sender, @Completion("#players") Player player, int success, @Completion("#sets") List<ArmorSet> armorSets) {

        if (player == null) {
            sender.sendMessage(CC.translate("&c&l(!)&c Invalid player."));
            return;
        }

        for (ArmorSet set : armorSets) {

            if (set == null) {
                sender.sendMessage(CC.translate("&c&l(!)&c Invalid ArmorSet type."));
                return;
            }

        }

        player.getInventory().addItem(new ArmorCrystalBuilder(success, armorSets).build());

    }

    @SubCommand("single")
    @WrongUsage("&cUsage: /armorcrystal single <player> <percent> <armorset>")
    @Permission("cosmic.admin")
    public void singleCommand(CommandSender sender, @Completion("#players") Player player, int success, @Completion("#sets") ArmorSet armorSet) {

        if (player == null) {
            sender.sendMessage(CC.translate("&c&l(!)&c Invalid player."));
            return;
        }

        if (armorSet == null) {
            sender.sendMessage(CC.translate("&c&l(!)&c Invalid ArmorSet type."));
            return;
        }

        player.getInventory().addItem(new ArmorCrystalBuilder(armorSet, success).build());

    }

}
