package me.viiral.cosmiccore.modules.skins.command;

import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.modules.skins.struct.item.SkinBuilder;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(CC.translate("&c&l(!)&c &c/giveskin <player> <types...>"));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) return true;

        if (args.length >= 2) {
            List<Skin> skins = new ArrayList<>();

            int length = args.length;
            int skip = 0;

            for (int i = 0; i < length; i++) {
                if (i == skip) continue;
                if (!SkinRegister.getInstance().isSkinFromName(args[i])) {
                    player.sendMessage(CC.translate("&C&l(!)&c Invalid Skin (" + args[i] + ")"));
                    return true;
                }
                skins.add(SkinRegister.getInstance().getSkinFromName(args[i]));
            }
            player.getInventory().addItem(new SkinBuilder(skins).build());
            sender.sendMessage(CC.translate("&a&l(!)&a Successfully gave " + player.getName() + " a skin."));
        }

        return true;
    }
}
