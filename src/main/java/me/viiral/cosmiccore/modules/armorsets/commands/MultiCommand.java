package me.viiral.cosmiccore.modules.armorsets.commands;

import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.items.ArmorCrystalBuilder;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MultiCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.isOp()) return true;

        if (args.length == 0) {
            sender.sendMessage(CC.translate("&c/multi <player> <percent> <types...>"));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) return true;

        if (args.length >= 3) {
            int success = Integer.parseInt(args[1]);

            if (success == 0) {
                success = 1;
            }

            if (success >= 101) {
                success = 100;
            }

            List<ArmorSet> sets = new ArrayList<>();

            int length = args.length;
            int skip = 1;

            for (int i = 0; i < length; i++) {
                if (i <= skip) continue;
                if (!CosmicCore.getInstance().getArmorSetRegister().isArmorSet(args[i])) {
                    player.sendMessage(CC.translate("&c&l(!)&c Invalid ArmorSet (" + args[i] + ")"));
                    return true;
                }
                sets.add(CosmicCore.getInstance().getArmorSetRegister().getArmorSetFromName(args[i]));
            }

            player.getInventory().addItem(new ArmorCrystalBuilder(success, sets).build());
            sender.sendMessage(CC.translate("&a&lSuccessfully gave " + player.getName() + " an armor crystal."));
        }

        return true;
    }
}
