package me.viiral.cosmiccore.modules.user.commands;

import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.potion.PotionEffectUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlessCommand implements CommandExecutor {

    // TODO: 20/09/2023 Add cooldown to command
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            PotionEffectUtils.bless((Player) commandSender);
            commandSender.sendMessage(CC.translate("&e&l(!)&e /bless: &fall negative effects have been removed."));
            return true;
        }
        return true;
    }
}
