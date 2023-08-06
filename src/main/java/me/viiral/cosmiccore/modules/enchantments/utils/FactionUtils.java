package me.viiral.cosmiccore.modules.enchantments.utils;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.entity.Player;

public final class FactionUtils {

    private FactionUtils() {

    }

    public static boolean cantPvPFaction(Player victim, Player attacker) {
        FPlayer VictimPlayer = FPlayers.getInstance().getByPlayer(victim);
        Faction VictimFac = VictimPlayer.getFaction();
        FPlayer AttackerPlayer = FPlayers.getInstance().getByPlayer(attacker);
        Faction AttackerFac = AttackerPlayer.getFaction();
        Relation relationBetween = VictimFac.getRelationTo(AttackerFac);
        return relationBetween != Relation.ENEMY && relationBetween != Relation.NEUTRAL;
    }
}
