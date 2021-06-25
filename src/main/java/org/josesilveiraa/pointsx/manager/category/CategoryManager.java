package org.josesilveiraa.pointsx.manager.category;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.object.Category;
import org.josesilveiraa.pointsx.object.ShopItem;
import org.josesilveiraa.pointsx.util.ItemBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryManager {

    private static final FileConfiguration CONFIG = PointsX.getShopConfiguration().getConfig();

    @SuppressWarnings("all")
    public static void loadCategories() {

        ConfigurationSection section = CONFIG.getConfigurationSection("categories");

        section.getKeys(false).forEach(key -> {
            int x = CONFIG.getInt("categories." + key + ".x");
            int y = CONFIG.getInt("categories." + key + ".y");
            int amount = CONFIG.getInt("categories." + key + ".amount");
            Material item = Material.valueOf(CONFIG.getString("categories." + key + ".item"));
            String name = CONFIG.getString("categories." + key + ".name");
            List<Component> lore = CONFIG.getStringList("categories." + key + ".lore").stream().map(it -> Component.text(it.replace("&", "§"))).collect(Collectors.toList());

            ItemStack itemStack = new ItemStack(item, amount);

            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Component.text(name));
            meta.lore(lore);
            itemStack.setItemMeta(meta);

            Category category = new Category(name, itemStack, x, y, new ChestGui(5, name));
            PointsX.getCategories().put(key, category);
        });
    }

    @SuppressWarnings("all")
    public static List<ShopItem> getItemsFromCategory(Category category) {

        List<ShopItem> toReturn = new ArrayList<>();
        String id = category.getIdentification();

        ConfigurationSection section = CONFIG.getConfigurationSection("items");

        section.getKeys(false).forEach(key -> {

            boolean executeCommands = CONFIG.getBoolean("items." + key + ".execute-commands");
            boolean addToInventory = CONFIG.getBoolean("items." + key + ".add-item-to-inventory");
            String itemCat = CONFIG.getString("items." + key + ".category");
            String name = CONFIG.getString("items." + key + ".name").replace("&", "§");
            double price = CONFIG.getDouble("items." + key + ".price");
            List<String> lore = CONFIG.getStringList("items." + key + ".lore").stream().map(it -> it.replace("&", "§").replace("{price}", String.valueOf(price))).collect(Collectors.toList());
            List<String> enchantments = CONFIG.getStringList("items." + key + ".enchantments");
            List<String> commands = CONFIG.getStringList("items." + key + ".commands").stream().map(it -> it.replace("{name}", name)).collect(Collectors.toList());
            Material material = Material.valueOf(CONFIG.getString("items." + key + ".item"));
            int amount = CONFIG.getInt("items." + key + ".amount");
            int x = CONFIG.getInt("items." + key + ".x");
            int y = CONFIG.getInt("items." + key + ".y");

            ItemBuilder itemBuilder = new ItemBuilder(material, amount)
                    .withName(name)
                    .withLore(lore);


            for(String enchantment : enchantments) {
                String enchantmentName = enchantment.split(":")[0];
                int level = Integer.parseInt(enchantment.split(":")[1]);

                itemBuilder.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase())), level);
            }

            if(itemCat.equals(id)) {
                toReturn.add(new ShopItem(itemBuilder.toItemStack(), x, y, price, executeCommands, addToInventory, commands));
            }
        });

        return toReturn;
    }

    @Nullable
    public static Category getByIdentification(String id) {
        if (PointsX.getCategories().containsKey(id)) {
            return PointsX.getCategories().get(id);
        }
        return null;
    }
}
