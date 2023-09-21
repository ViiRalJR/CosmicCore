package me.viiral.cosmiccore.modules.user.commands;

import me.viiral.cosmiccore.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    // TODO: 20/09/2023 Add cooldown to command
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            player.setHealth(player.getMaxHealth());
            player.sendMessage(CC.translate("&a&l(!)&a /heal: &fyour health has been regenerated."));
            return true;
        }
        return true;
    }
}
