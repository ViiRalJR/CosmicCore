package me.viiral.cosmiccore.modules.mask.masks;

import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.utils.CC;

import java.util.Collections;
import java.util.List;

public class BunnyMask extends Mask {

    public BunnyMask() {
        super("Bunny", "");
    }


    @Override
    public String getColor() {
        return CC.LightPurple;
    }

    @Override
    public List<String> getLore() {
        return Collections.singletonList("&c1.65x Mobs from Spawners in Chunk");
    }
}
