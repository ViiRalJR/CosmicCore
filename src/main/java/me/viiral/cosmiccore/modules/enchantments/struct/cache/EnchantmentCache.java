package me.viiral.cosmiccore.modules.enchantments.struct.cache;

import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.utils.cache.Cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static me.viiral.cosmiccore.modules.NbtTags.ENCHANTMENT_CACHE_ID;

public class EnchantmentCache extends Cache {

    private final HashMap<String, EnchantInfo> playerEnchantments;

    public EnchantmentCache() {
        super(ENCHANTMENT_CACHE_ID);
        this.playerEnchantments = new HashMap<>();
    }


    public boolean hasEnchantment(Enchantment enchantment) {
        return this.playerEnchantments.containsKey(enchantment.getID());
    }

    public boolean hasEnchantment(String enchantmentID) {
        return this.playerEnchantments.containsKey(enchantmentID);
    }

    public EnchantInfo getEnchantInfo(Enchantment enchantment) {
        return this.playerEnchantments.get(enchantment.getID());
    }

    public EnchantInfo getEnchantInfo(String enchantmentID) {
        return this.playerEnchantments.get(enchantmentID);
    }

    public void removeEnchantment(Enchantment enchantment, int level) {
        if (!this.hasEnchantment(enchantment)) return;

        if (this.getEnchantInfo(enchantment).getAmount() > 1) {
            this.getEnchantInfo(enchantment).decrementAmount();
            this.getEnchantInfo(enchantment).removeFromCurrentLevel(level);
            return;
        }

        this.playerEnchantments.remove(enchantment.getID());
    }

    public void addEnchantment(Enchantment enchantment, int level) {
        if (this.hasEnchantment(enchantment) && enchantment.isStackable()) {
            this.getEnchantInfo(enchantment).incrementAmount();
            this.getEnchantInfo(enchantment).addToCurrentLevel(level);
            return;
        }

        this.playerEnchantments.put(enchantment.getID(), new EnchantInfo(level, 1));
    }

    public void clearEnchantments() {
        this.playerEnchantments.clear();
    }

    public Set<Enchantment> getEnchants() {
        Set<Enchantment> enchants = new HashSet<>();
        for (String enchantName : this.playerEnchantments.keySet()) {
            enchants.add(EnchantRegister.getInstance().getEnchantmentFromID(enchantName));
        }
        return enchants;
    }

    public Set<String> getEnchantIDs() {
        return this.playerEnchantments.keySet();
    }
}

