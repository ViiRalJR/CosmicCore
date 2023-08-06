package me.viiral.cosmiccore.modules.armorsets.struct.items;

import com.massivecraft.factions.util.CC;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.utils.MiscUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CrystalArmorBuilder {

    private NBTItem nbtItem;
    private final NBTCompound crystalCompound;

    public CrystalArmorBuilder(ItemStack itemStack) {
        this(new NBTItem(itemStack));
    }

    public CrystalArmorBuilder(NBTItem item) {
        this.nbtItem = item;
        this.crystalCompound = this.getOrCreateCrystalCompound();
    }

    public boolean hasCrystal() {
        if (this.crystalCompound.hasTag("crystalType")) {
            return this.crystalCompound.getString("crystalType") != null && !this.crystalCompound.getString("crystalType").equals("null");
        }
        if (this.crystalCompound.hasTag("armorSetType")) {
            return this.crystalCompound.getString("armorSetType") != null && !this.crystalCompound.getString("armorSetType").equals("null");
        }
        return false;
    }

    public CrystalArmorBuilder applyMultiCrystal(List<ArmorSet> sets) {
        StringBuilder builder = new StringBuilder();
        for (ArmorSet set : sets) {
            builder.append(set.getID()).append("@;;");
        }
        this.crystalCompound.setString("crystalType", builder.toString());
        this.addCrystalToLore(sets);
        return this;
    }

    private void addCrystalToLore(List<ArmorSet> sets) {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        StringBuilder builder = new StringBuilder();
        builder.append("&6&lArmor Crystal (");
        int loop = sets.size();
        for (int i = 0; i < loop; i++) {
            if (i == loop - 1)
                builder.append(sets.get(i).getColor()).append("&l").append(sets.get(i).getName()).append("&6&l)");
            else
                builder.append(sets.get(i).getColor()).append("&l").append(sets.get(i).getName()).append("&6, ");
        }

        List<String> lore = this.getLore();

        lore.add(builder.toString());
        itemMeta.setLore(CC.translate(lore));
        this.nbtItem.getItem().setItemMeta(itemMeta);
    }

    public CrystalArmorBuilder removeCrystal() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String crystalLine = CC.translate("&6&lArmor Crystal (");

        List<String> lore = this.getLore()
                .stream()
                .filter(str -> !str.startsWith(crystalLine))
                .collect(Collectors.toList());

        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        NBTItem item = new NBTItem(itemStack);
        item.getOrCreateCompound("cosmicData").removeKey("crystalType");
        this.nbtItem = item;

        return this;
    }


    public List<String> getLore() {
        return MiscUtils.defaultIfNull(this.nbtItem.getItem().getItemMeta().getLore(), new ArrayList<>());
    }


    private NBTCompound getOrCreateCrystalCompound() {
        NBTCompound crystalCompound = this.nbtItem.getCompound("cosmicData");
        if (crystalCompound == null) crystalCompound = this.nbtItem.addCompound("cosmicData");
        return crystalCompound;
    }

    public ItemStack build() {
        return this.nbtItem.getItem();
    }
}
