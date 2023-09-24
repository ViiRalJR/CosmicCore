package me.viiral.cosmiccore.modules.armorsets.struct.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.enchantments.struct.items.CustomItem;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.viiral.cosmiccore.modules.NbtTags.*;

@Getter
public class ArmorCrystalBuilder extends CustomItem {

    private static final String ARMOR_CRYSTAL_FORMAT = "&6&lArmor Crystal (";
    private static final String NBT_SEPARATOR = "@;;";
    private static final Material CRYSTAL_MATERIAL = Material.NETHER_STAR;
    private static final String SUCCESS_RATE_FORMAT = "&a%d%% Success Rate";
    private static final List<String> LORE = Arrays.asList(
            "&7Can be applied to any non",
            "&7armor set that is not",
            "&7already equipped with a",
            "&7bonus crystal to gain",
            "&7a passive advantage",
            "",
            "&6&lCrystal Bonus:"
    );

    private int success;

    public ArmorCrystalBuilder(ItemStack itemStack) {
        super(ARMOR_CRYSTAL);
        this.nbtItem = new NBTItem(itemStack);
        this.success = getSuccessRate();
    }

    public ArmorCrystalBuilder(ArmorSet set, int success) {
        super(ARMOR_CRYSTAL);
        List<ArmorSet> sets = Collections.singletonList(set);
        this.nbtItem = buildNBTItem(success, sets);
    }

    public ArmorCrystalBuilder(int success, List<ArmorSet> sets) {
        super(ARMOR_CRYSTAL);
        if (sets.size() == 1) this.nbtItem = buildNBTItem(success, sets.get(0));
        else this.nbtItem = buildNBTItem(success, sets);
    }

    private NBTItem buildNBTItem(int success, ArmorSet set) {
        ItemStack itemStack = new ItemBuilder(CRYSTAL_MATERIAL)
                .setName(ARMOR_CRYSTAL_FORMAT + set.getColor() + "&l" + set.getName() + "&6&l" + ")")
                .addLore(String.format(SUCCESS_RATE_FORMAT, success))
                .addLore(LORE)
                .addLore(set.getCrystalLore())
                .colorize()
                .build();

        NBTItem item = new NBTItem(itemStack);
        item.setString("randomUUID", UUID.randomUUID().toString());
        item.setInteger(SUCCESS_RATE_STRING, success);

        item.getOrCreateCompound(COSMIC_DATA_STRING).setString(ARMOR_CRYSTAL, set.getID());
        return item;
    }

    private NBTItem buildNBTItem(int success, List<ArmorSet> sets) {
        int armorSetsLength = sets.size();
        StringBuilder name = new StringBuilder(ARMOR_CRYSTAL_FORMAT);
        List<String> lore = new ArrayList<>();
        StringBuilder nbt = new StringBuilder();
        for (int i = 0; i < armorSetsLength; i++) {
            ArmorSet set = sets.get(i);
            name.append(set.getColor())
                    .append(ChatColor.BOLD)
                    .append(set.getName().charAt(0))
                    .append(ChatColor.GOLD);

            if (i != armorSetsLength - 1) {
                name.append(", ");
            } else {
                name.append(ChatColor.BOLD).append(")");
            }

            lore.addAll(set.getCrystalLore());
            nbt.append(set.getID()).append(NBT_SEPARATOR);

        }


        ItemStack itemStack = new ItemBuilder(CRYSTAL_MATERIAL)
                .setName(name.toString())
                .addLore(String.format(SUCCESS_RATE_FORMAT, success))
                .addLore(LORE)
                .addLore(lore)
                .colorize()
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("randomUUID", UUID.randomUUID().toString());
        nbtItem.setInteger(SUCCESS_RATE_STRING, success);

        nbtItem.getOrCreateCompound(COSMIC_DATA_STRING).setString(ARMOR_CRYSTAL, nbt.toString());
        return nbtItem;
    }

    private int generateChance() {
        return new Random().nextInt(101);
    }

    public int getSuccessRate() {
        return this.nbtItem.getInteger(SUCCESS_RATE_STRING);
    }

    public int getDestroyRate() {
        return this.nbtItem.getInteger(DESTROY_RATE_STRING);
    }

    public List<ArmorSet> getArmorSets() {
        return Optional.ofNullable(this.nbtItem.getOrCreateCompound("cosmicData").getString("armorCrystal"))
                .map(s -> s.split(NBT_SEPARATOR))
                .map(Arrays::stream).orElseGet(Stream::empty)
                .map(CosmicCore.getInstance().getArmorSetRegister()::getArmorSetFromID)
                .collect(Collectors.toList());
    }

    public ItemStack build() {
        return this.nbtItem.getItem();
    }
}
