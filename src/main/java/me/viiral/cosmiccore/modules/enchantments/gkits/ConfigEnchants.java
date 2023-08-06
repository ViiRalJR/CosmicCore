package me.viiral.cosmiccore.modules.enchantments.gkits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ConfigEnchants {

    private final Map<Enchantment, MinMaxValue> enchants;

}
