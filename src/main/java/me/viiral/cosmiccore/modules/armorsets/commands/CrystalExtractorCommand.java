package me.viiral.cosmiccore.modules.armorsets.commands;

import me.viiral.cosmiccore.modules.armorsets.struct.items.CrystalExtractorBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CrystalExtractorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.isOp()) return true;

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) return true;

        int percent = Integer.parseInt(args[1]);

        player.getInventory().addItem(new CrystalExtractorBuilder(percent).build());

        return true;
    }
}
