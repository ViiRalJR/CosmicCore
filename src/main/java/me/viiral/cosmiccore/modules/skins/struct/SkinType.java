package me.viiral.cosmiccore.modules.skins.struct;

import lombok.Getter;
import me.viiral.cosmiccore.utils.armor.ArmorUtils;
import org.bukkit.Material;

@Getter
public enum SkinType {

    HELMET("Helmets", ArmorUtils.helmet),
    AMULET("Chestplates", ArmorUtils.chestplate),
    BACKPACK("Chestplates", ArmorUtils.chestplate),
    BELT("Leggings", ArmorUtils.leggings),
    BOOTS("Boots", ArmorUtils.boots);

    private final String label;
    private final Material[] applicable;

    SkinType(String label, Material[] applicable) {
        this.label = label;
        this.applicable = applicable;
    }
}
