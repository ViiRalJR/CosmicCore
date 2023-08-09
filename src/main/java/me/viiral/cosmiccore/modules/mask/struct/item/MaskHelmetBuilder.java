package me.viiral.cosmiccore.modules.mask.struct.item;

import com.massivecraft.factions.util.CC;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MaskHelmetBuilder {

    private static final String COSMIC_DATA = "cosmicData";
    private static final String MASK_TYPE = "maskType";
    private static final String MASK_FORMAT = "&f&lMask (";
    private static final String MASK_DELIMITER = "@;;";

    private NBTItem nbtItem;
    private final NBTCompound cosmicData;

    public MaskHelmetBuilder(ItemStack itemStack) {
        this(new NBTItem(itemStack));
    }

    public MaskHelmetBuilder(NBTItem item) {
        this.nbtItem = item;
        this.cosmicData = nbtItem.getOrCreateCompound(COSMIC_DATA);
    }

    public boolean hasMask() {
        if (this.cosmicData.hasTag(MASK_TYPE)) {
            return this.cosmicData.getString(MASK_TYPE) != null && !this.cosmicData.getString(MASK_TYPE).equals("null");
        }
        return false;
    }

    public MaskHelmetBuilder applyMask(List<Mask> masks) {
        String maskTypes = masks.stream()
                .map(Mask::getName)
                .collect(Collectors.joining(MASK_DELIMITER));

        this.cosmicData.setString(MASK_TYPE, maskTypes);
        this.addMaskLore(masks);

        return this;
    }

    private void addMaskLore(List<Mask> masks) {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String maskNames = masks.stream()
                .map(mask -> mask.getColor() + "&l" + mask.getName())
                .collect(Collectors.joining(", ", MASK_FORMAT, "&f&l)"));

        List<String> lore = getLore();
        lore.add(maskNames);

        itemMeta.setLore(CC.translate(lore));
        itemStack.setItemMeta(itemMeta);
    }

    public MaskHelmetBuilder removeMask() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String maskLine = CC.translate(MASK_FORMAT);

        List<String> lore = this.getLore()
                .stream()
                .filter(str -> !str.startsWith(maskLine))
                .collect(Collectors.toList());

        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        NBTItem item = new NBTItem(itemStack);
        item.getOrCreateCompound(COSMIC_DATA).removeKey(MASK_TYPE);
        this.nbtItem = item;
        return this;
    }

    public List<Mask> getMasks() {
        String maskString = Optional.ofNullable(this.cosmicData.getString("maskType")).orElse("");
        return Arrays.stream(maskString.split(MASK_DELIMITER))
                .map(s -> MaskRegister.getInstance().getMaskFromName(s))
                .collect(Collectors.toList());
    }

    public List<String> getLore() {
        ItemMeta itemMeta = this.nbtItem.getItem().getItemMeta();
        return Optional.ofNullable(itemMeta.getLore()).orElseGet(ArrayList::new);
    }

    public ItemStack build() {
        return this.nbtItem.getItem();
    }
}
