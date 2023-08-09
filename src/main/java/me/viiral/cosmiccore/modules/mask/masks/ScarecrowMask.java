package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Collections;
import java.util.List;

public class ScarecrowMask extends Mask {

    public ScarecrowMask() {
        super("Scarecrow", "");
    }


    @Override
    public String getColor() {
        return CC.Yellow;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&cInfinite Food");
    }

    @EventHandler
    public void onFoodLoos(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
