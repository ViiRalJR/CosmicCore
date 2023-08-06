package me.viiral.cosmiccore.modules.enchantments.gkits;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Getter
public class MinMaxValue {

    private final int min, max;

    public int getRandomLevel() {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
