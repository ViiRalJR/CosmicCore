package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;

public class DriftayMask extends Mask {

    public DriftayMask() {
        super("Driftay", "");
    }

    @Override
    public String getColor() {
        return CC.Red;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cDriftay test lore for mask");
    }
}
