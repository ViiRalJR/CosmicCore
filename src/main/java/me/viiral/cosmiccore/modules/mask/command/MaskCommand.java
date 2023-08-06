package me.viiral.cosmiccore.modules.mask.command;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.modules.mask.struct.item.MaskBuilder;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MaskCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(CC.translate("&c&l(!) &c/givemask <player> <types...>"));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) return true;

        if (args.length >= 2) {

            List<Mask> masks = new ArrayList<>();

            int length = args.length;
            int skip = 0;

            for (int i = 0; i < length; i++) {
                if (i == skip) continue;
                if (!MaskRegister.getInstance().isMaskFromName(args[i])) {
                    player.sendMessage(CC.translate("&c&l(!)&c Invalid Mask (" + args[i] + ")"));
                    return true;
                }
                masks.add(MaskRegister.getInstance().getMaskFromName(args[i]));
            }
            player.getInventory().addItem(new MaskBuilder(masks).build());
            sender.sendMessage(CC.translate("&a&l(!)&a Successfully gave " + player.getName() + " a mask."));
        }

        return true;
    }
}
