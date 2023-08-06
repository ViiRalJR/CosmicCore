package me.viiral.cosmiccore.modules.enchantments.commands;

import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.modules.enchantments.EnchantsAPI;
import org.bukkit.entity.Player;

@Command("apply")
@Alias("reapply")
public class ApplyCommand  extends CommandBase {

    @Default
    public void mainCommand(Player sender)  {
        EnchantsAPI.reprocEnchants(sender);
    }
}
