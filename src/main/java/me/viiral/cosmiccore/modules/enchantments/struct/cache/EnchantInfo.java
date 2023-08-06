package me.viiral.cosmiccore.modules.enchantments.struct.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnchantInfo {

    private Integer level;
    private Integer amount;

    public void addToCurrentLevel(int level) {
        this.level += level;
    }

    public void removeFromCurrentLevel(int level) {
        this.level -= level;
    }

    public void incrementAmount() {
        this.amount++;
    }

    public void decrementAmount() {
        this.amount--;
    }
}
