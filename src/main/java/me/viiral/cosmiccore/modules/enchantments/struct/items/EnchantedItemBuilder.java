package me.viiral.cosmiccore.modules.enchantments.struct.items;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.MiscUtils;
import me.viiral.cosmiccore.utils.RomanNumeral;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

import static me.viiral.cosmiccore.modules.NbtTags.*;

public class EnchantedItemBuilder {

    private final NBTItem nbtItem;
    private final NBTCompound enchantsCompound;

    public EnchantedItemBuilder(ItemStack itemStack) {
        this(new NBTItem(itemStack));
    }

    public EnchantedItemBuilder(NBTItem item) {
        this.nbtItem = item;
        this.enchantsCompound = this.getOrCreateEnchantsCompound();
        if (!this.nbtItem.hasKey(SLOTS_AMOUNT_STRING))
            this.nbtItem.setInteger(SLOTS_AMOUNT_STRING, 9);
        if (!this.nbtItem.hasKey(PROTECTED_STRING))
            this.nbtItem.setBoolean(PROTECTED_STRING, false);
    }

    private NBTCompound getOrCreateEnchantsCompound() {
        NBTCompound enchantsCompound = this.nbtItem.getCompound(ENCHANTS_TAG);
        if (enchantsCompound == null) enchantsCompound = this.nbtItem.addCompound(ENCHANTS_TAG);
        return enchantsCompound;
    }
    public HashMap<Enchantment, Integer> getEnchantments() {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        this.enchantsCompound.getKeys().forEach(key -> {
            int level = this.enchantsCompound.getInteger(key);
            Enchantment enchantment = EnchantRegister.getInstance().getEnchantmentFromID(key);
            enchants.put(enchantment, level);
        });

        return enchants;
    }

    public HashMap<String, Integer> getEnchantmentsAsString() {
        HashMap<String, Integer> enchants = new HashMap<>();

        this.enchantsCompound.getKeys().forEach(key -> {
            int level = this.enchantsCompound.getInteger(key);
            enchants.put(key, level);
        });

        return enchants;
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return this.getEnchantments().containsKey(enchantment);
    }

    public boolean hasEnchantment(String enchantment) {
        return this.getEnchantmentsAsString().containsKey(enchantment);
    }

    public boolean hasEnchantments() {
        return this.getEnchantments().size() > 0;
    }

    // returns 0 if not applies
    public int getEnchantmentLevel(Enchantment enchantment) {
        if (!this.hasEnchantment(enchantment)) return 0;
        return this.enchantsCompound.getInteger(enchantment.getID());
    }

    public int getEnchantmentLevel(String enchantment) {
        if (!this.hasEnchantment(enchantment)) return 0;
        return this.enchantsCompound.getInteger(enchantment);
    }

    public int getAmountOfEnchantments() {
        return this.getEnchantments().keySet().size();
    }

    public boolean hasSoulTrackerAlreadyApplied() {
        return this.nbtItem.hasKey("soul-tracker");
    }

    public EnchantTier getSoulTrackerTier() {
        return EnchantTier.valueOf(this.nbtItem.getString("soul-tracker-tier"));
    }

    public int getHarvestedSouls() {
        return MiscUtils.defaultIfNull(this.nbtItem.getInteger("soul-tracker"), 0);
    }

    public void incrementHarvestedSouls() {
        this.nbtItem.setInteger("soul-tracker", this.getHarvestedSouls() + 1);
    }

    public void setHarvestedSouls(int amount) {
        this.nbtItem.setInteger("soul-tracker", amount);
    }

    public void removeHarvestedSouls(int amount) {
        this.nbtItem.setInteger("soul-tracker", Math.max(this.getHarvestedSouls() - amount, 0));
    }

    public void resetHarvestedSouls() {
        this.nbtItem.setInteger("soul-tracker", 0);
    }

    public EnchantedItemBuilder applySoulTracker(SoulTrackerBuilder soulTrackerBuilder) {
        this.nbtItem.setInteger("soul-tracker", 0);
        this.nbtItem.setString("soul-tracker-tier", soulTrackerBuilder.getEnchantTier().name());
        this.addSoulsHarvestedToLore();
        return this;
    }

    public EnchantedItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantsCompound.setInteger(enchantment.getID(), level);
        ItemStack item = this.nbtItem.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();

        lore.add(this.getEnchantments().keySet().size() - 1, enchantment.getTier().getColor() + enchantment.getName() + " " + RomanNumeral.convertToRoman(level));

        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
        return this;
    }

    public void addEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach((this::addEnchantment));
    }

    public Enchantment selectRandomEnchantment() {
        Random random = new Random();
        int number = random.nextInt(this.getEnchantments().size());
        Enchantment[] enchantments = this.getEnchantments().keySet().toArray(new Enchantment[0]);
        return enchantments[number];

    }

    public void removeEnchantment(Enchantment enchantment) {
        this.enchantsCompound.removeKey(enchantment.getID());
        ItemStack item = this.nbtItem.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) return;

        for (int i = 0; i < this.getEnchantments().size() + 1; i++) {
            if (lore.get(i).contains(enchantment.getName())) {
                lore.remove(i);
                break;
            }
        }


        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    public int getSlots() {
        return MiscUtils.defaultIfNull(this.nbtItem.getInteger(SLOTS_AMOUNT_STRING), 9);
    }

    public void setSlots(int slots) {
        this.nbtItem.setInteger(SLOTS_AMOUNT_STRING, slots);
    }

    public boolean isProtected() {
        return this.nbtItem.getBoolean(PROTECTED_STRING);
    }

    public boolean isHolyProtected() {
        return this.nbtItem.getBoolean(HOLY_PROTECTED_STRING);
    }

    public void setProtected(boolean status) {
        this.nbtItem.setBoolean(PROTECTED_STRING, status);

        if (status) {
            this.addProtectedToLore();
            return;
        }

        this.removeProtectedFromLore();
    }

    public void setHolyProtected(boolean status) {
        this.nbtItem.setBoolean(HOLY_PROTECTED_STRING, status);

        if (status) {
            this.addHolyProtectedToLore();
            return;
        }

        this.removeHolyProtectedFromLore();
    }

    private void addHolyProtectedToLore() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = this.getLore();
        String protectedLoreLine = CC.translate("&f&lPROTECTED (&6&l*HOLY*&f&l)");
        if (lore.contains(protectedLoreLine)) return;
        lore.add(protectedLoreLine);
        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    private void removeHolyProtectedFromLore() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String protectedLoreLine = CC.translate("&f&lPROTECTED (&6&l*HOLY*&f&l)");
        List<String> lore = this.getLore()
                .stream()
                .filter(str -> !str.equals(protectedLoreLine))
                .collect(Collectors.toList());
        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    private void addProtectedToLore() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = this.getLore();
        String protectedLoreLine = CC.translate("&f&lPROTECTED");

        if (lore.contains(protectedLoreLine)) return;

        lore.add(protectedLoreLine);

        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    private void removeProtectedFromLore() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String protectedLoreLine = CC.translate("&f&lPROTECTED");

        List<String> lore = this.getLore()
                .stream()
                .filter(str -> !str.equals(protectedLoreLine))
                .collect(Collectors.toList());

        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    public EnchantedItemBuilder sortEnchantLore() {
        if (this.getLore().isEmpty()) return this;

        List<String> sortedEnchantsLore = this.getEnchantments()
                .keySet()
                .stream()
                .sorted(Comparator.comparingInt(Enchantment::getEnchantTierWeight))
                .map(enchantment -> enchantment.getTier().getColor() + enchantment.getName() + " " + RomanNumeral.convertToRoman(this.getEnchantmentLevel(enchantment)))
                .collect(Collectors.toList());

        List<String> newLore = this.getLore();

        for (int i = 0; i < this.getAmountOfEnchantments(); i++) {
            newLore.set(i, sortedEnchantsLore.get(i));
        }

        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(newLore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
        return this;
    }

    private void addSoulsHarvestedToLore() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String soulHarvesterLoreLine = CC.translate("&6&lSouls Harvested: ");

        List<String> lore = this.getLore()
                .stream()
                .filter(str -> !str.startsWith(soulHarvesterLoreLine))
                .collect(Collectors.toList());

        lore.add(soulHarvesterLoreLine + this.getHarvestedSouls());

        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    public void updateSoulsHarvested() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String soulHarvesterLoreLine = CC.translate("&6&lSouls Harvested: ");

        List<String> lore = this.getLore();
        lore.replaceAll(str -> str.startsWith(soulHarvesterLoreLine) ? soulHarvesterLoreLine + this.getHarvestedSouls() : str);

        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    public void updateSlotsLore() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = this.getLore();
        int amountOfEnchants = this.getAmountOfEnchantments();

        if (lore.size() > amountOfEnchants && lore.get(amountOfEnchants).contains(" Enchantment Slots ") && !lore.get(amountOfEnchants).startsWith(CC.translate("&6&lLORE"))) {
            lore.remove(amountOfEnchants);
        }

        lore.add(amountOfEnchants, CC.translate("&a&l" + this.getSlots() + " Enchantment Slots &7(Orb [&a+" + (this.getSlots() - 9) + "&7])"));

        itemMeta.setLore(lore);
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    public EnchantedItemBuilder addEnchantAmountToName() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String itemName = itemMeta.getDisplayName();

        if (itemName != null && itemName.contains(CC.translate("&d&l[&b&l&n"))) {
            itemName = itemName.substring(0, itemName.lastIndexOf(" "));
        }

        String name = MiscUtils.defaultIfNull(itemName, CC.translate("&b" + MiscUtils.toNiceString(itemStack.getType().name())));

        itemMeta.setDisplayName(CC.translate(name + " &d&l[&b&l&n" + this.getAmountOfEnchantments() + "&d&l]"));
        this.nbtItem.getItem().setItemMeta(itemMeta);
        return this;
    }

    public List<String> getLore() {
        return MiscUtils.defaultIfNull(this.nbtItem.getItem().getItemMeta().getLore(), new ArrayList<>());
    }

    public ItemStack build() {
        return this.nbtItem.getItem();
    }

}

