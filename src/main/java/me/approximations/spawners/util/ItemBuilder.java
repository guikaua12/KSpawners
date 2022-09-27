package me.approximations.spawners.util;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

//    private static final ItemStack SKULL_ITEM = TypeUtil.convertFromLegacy("SKULL_ITEM", 3);

    private final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material type) {
        this(new ItemStack(type));
    }

    public ItemBuilder(Material type, int data) {
        this(new ItemStack(type, 1, (short) data));
    }

    public ItemBuilder(String name, byte... ignore) {
        item = new ItemStack(TypeUtil.getMaterialFromLegacy("SKULL_ITEM"), 1, (short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        try {
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        } catch (Throwable e) {
            meta.setOwner(name);
        }

        item.setItemMeta(meta);
    }

    public ItemBuilder(String url) {
        item = NBTEditor.getHead(url);
    }

    public ItemBuilder(Material type, Color color) {
        item = new ItemStack(type);

        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
    }

    public ItemBuilder changeItemMeta(Consumer<ItemMeta> consumer) {
        ItemMeta itemMeta = item.getItemMeta();
        consumer.accept(itemMeta);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setName(String name) {
        return changeItemMeta(it -> it.setDisplayName(ColorUtil.colored(name)));
    }

    public ItemBuilder setLore(String... lore) {
        return changeItemMeta(it -> it.setLore(Arrays.asList(ColorUtil.colored(lore))));
    }

    public ItemBuilder setLore(List<String> lore) {
        return changeItemMeta(it -> it.setLore(lore));
    }

    public List<String> getLore() {
        return item.getItemMeta().getLore();
    }

    public ItemStack wrap() {
        return item;
    }

}