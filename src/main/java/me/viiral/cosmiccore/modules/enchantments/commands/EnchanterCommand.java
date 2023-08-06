package me.viiral.cosmiccore.modules.enchantments.commands;

import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import me.viiral.cosmiccore.modules.enchantments.inventories.EnchanterUI;
import org.bukkit.entity.Player;

@Command("enchanter")
@Alias("ce")
public class EnchanterCommand extends CommandBase {

    @Default
    public void mainCommand(Player sender)  {
        EnchanterUI enchanterUI = new EnchanterUI();
        enchanterUI.getEnchantersUI().open(sender);
    }
}
