package me.viiral.cosmiccore.modules.enchantments.struct.cache;

import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.utils.cache.cooldown.CurrentCooldown;

public class EnchantCooldownCache extends CurrentCooldown {

    public EnchantCooldownCache(Enchantment enchantment, long duration) {
        super("enchant_cooldown_" + enchantment.getID(), duration);
    }

}
