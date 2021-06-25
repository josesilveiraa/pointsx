package org.josesilveiraa.pointsx.manager.shop;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.manager.category.CategoryManager;
import org.josesilveiraa.pointsx.object.Category;
import org.josesilveiraa.pointsx.object.ShopItem;

public final class ShopManager {

    public static void openGui(@NotNull Player player) {
        ChestGui gui = new ChestGui(5, "Shop");

        gui.setOnGlobalClick((event) -> event.setCancelled(true));

        StaticPane staticPane = new StaticPane(0, 0, 9, 5);

        for(Category category : PointsX.getCategories().values()) {
            staticPane.addItem(new GuiItem(category.getItemStack(), e -> {

                player.closeInventory();
                showCategoryInventory(category, player);

            }), category.getX(), category.getY());
        }

        gui.addPane(staticPane);

        staticPane.setVisible(true);


        gui.show(player);
    }

    public static void showCategoryInventory(@NotNull Category category, @NotNull Player player) {
        ChestGui gui = category.getChestGui();

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        StaticPane staticPane = new StaticPane(0, 0, 9, 5);

        for(ShopItem shopItem : CategoryManager.getItemsFromCategory(category)) {
            staticPane.addItem(new GuiItem(shopItem.getItemStack()), shopItem.getX(), shopItem.getY());

        }

        gui.addPane(staticPane);
        gui.show(player);

    }

}
