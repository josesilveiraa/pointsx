package org.josesilveiraa.volcano.item;

import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    @Setter private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder withName(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.displayName(Component.text(name));
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public ItemBuilder withLore(List<String> lore) {
        List<Component> list = new ArrayList<>();

        for(String line : lore) {
            list.add(Component.text(line));
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.lore(list);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder withLore(String... lore) {
        List<Component> list = new ArrayList<>();

        for(String line : lore) {
            list.add(Component.text(line));
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.lore(list);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStack toItemStack() {
        return this.itemStack;
    }

}
