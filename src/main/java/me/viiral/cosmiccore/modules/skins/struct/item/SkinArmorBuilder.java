package me.viiral.cosmiccore.modules.skins.struct.item;

import com.massivecraft.factions.util.CC;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.viiral.cosmiccore.modules.mask.struct.Mask;
import me.viiral.cosmiccore.modules.skins.struct.Skin;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkinArmorBuilder {

    private static final String COSMIC_DATA = "cosmicData";
    private static final String SKIN_TYPE = "skinType";
    private static final String SKIN_FORMAT = "&f&lItem Skin (";
    private static final String SKIN_DELIMITER = "@;;";

    private NBTItem nbtItem;
    private final NBTCompound cosmicData;

    public SkinArmorBuilder(ItemStack itemStack) {
        this(new NBTItem(itemStack));
    }

    public SkinArmorBuilder(NBTItem item) {
        this.nbtItem = item;
        this.cosmicData = nbtItem.getOrCreateCompound(COSMIC_DATA);
    }

    public boolean hasSkin() {
        if (this.cosmicData.hasTag(SKIN_TYPE)) {
            return this.cosmicData.getString(SKIN_TYPE) != null && !this.cosmicData.getString(SKIN_TYPE).equals("null");
        }
        return false;
    }

    public SkinArmorBuilder applySkin(List<Skin> skins) {
        String skinTypes = skins.stream()
                .map(Skin::getName)
                .collect(Collectors.joining(SKIN_DELIMITER));

        this.cosmicData.setString(SKIN_TYPE, skinTypes);
        this.addSkinLore(skins);
        return this;
    }

    private void addSkinLore(List<Skin> skins) {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String skinNames = skins.stream()
                .map(skin -> skin.getColor() + "&l" + skin.getName())
                .collect(Collectors.joining(", ", SKIN_FORMAT, "&f&l)"));

        List<String> lore = getLore();
        lore.add(skinNames);

        itemMeta.setLore(CC.translate(lore));
        itemStack.setItemMeta(itemMeta);
    }

    public SkinArmorBuilder removeSkin() {
        ItemStack itemStack = this.nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String skinLine = CC.translate(SKIN_FORMAT);

        List<String> lore = this.getLore()
                .stream()
                .filter(str -> !str.startsWith(skinLine))
                .collect(Collectors.toList());

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        NBTItem item = new NBTItem(itemStack);
        item.getOrCreateCompound(COSMIC_DATA).removeKey(SKIN_TYPE);
        this.nbtItem = item;
        return this;
    }

    public List<Skin> getSkins() {
        String skinString = Optional.ofNullable(this.cosmicData.getString("skinType")).orElse("");
        return Arrays.stream(skinString.split(SKIN_DELIMITER))
                .map(s -> SkinRegister.getInstance().getSkinFromName(s))
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
