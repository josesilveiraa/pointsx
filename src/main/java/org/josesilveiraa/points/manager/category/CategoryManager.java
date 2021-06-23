package org.josesilveiraa.points.manager.category;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.josesilveiraa.points.PointsX;
import org.josesilveiraa.points.object.Category;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryManager {

    private static final FileConfiguration CONFIG = PointsX.getShopConfiguration().getConfig();

    @SuppressWarnings("all")
    public static void loadCategories() {

        ConfigurationSection section = CONFIG.getConfigurationSection("categories");

        section.getKeys(false).forEach(key -> {
            int slot = CONFIG.getInt("categories." + key + ".slot");
            Material item = Material.valueOf(CONFIG.getString("categories." + key + ".item"));
            int amount = CONFIG.getInt("categories." + key + ".amount");
            String name = CONFIG.getString("categories." + key + ".name").replace("&", "§");
            List<Component> lore = CONFIG.getStringList("categories." + key + ".lore").stream().map(it -> Component.text(it.replace("&", "§"))).collect(Collectors.toList());

            ItemStack itemStack = new ItemStack(item, amount);

            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Component.text(name));
            meta.lore(lore);
            itemStack.setItemMeta(meta);

            Category category = new Category(key, itemStack, slot);
            PointsX.getCategories().put(key, category);
        });
    }

    @Nullable
    public static Category getByIdentification(String id) {
        if(PointsX.getCategories().containsKey(id)) {
            return PointsX.getCategories().get(id);
        }
        return null;
    }
}
