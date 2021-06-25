package org.josesilveiraa.pointsx.manager.shop;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.api.event.ShopBuyItemEvent;
import org.josesilveiraa.pointsx.api.event.ShopOpenEvent;
import org.josesilveiraa.pointsx.manager.EventDispatcher;
import org.josesilveiraa.pointsx.manager.UserManager;
import org.josesilveiraa.pointsx.manager.category.CategoryManager;
import org.josesilveiraa.pointsx.object.Category;
import org.josesilveiraa.pointsx.object.ShopItem;
import org.josesilveiraa.pointsx.object.User;

import java.util.List;
import java.util.stream.Collectors;

public final class ShopManager {

    public static void openGui(@NotNull Player player) {


        ChestGui gui = new ChestGui(5, "Shop");

        ShopOpenEvent shopOpenEvent = new ShopOpenEvent(player, gui);
        EventDispatcher.dispatch(shopOpenEvent);

        if(shopOpenEvent.isCancelled()) {
            return;
        }

        gui.setOnGlobalClick(event -> event.setCancelled(true));

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

        User user = UserManager.getByUuid(player.getUniqueId());

        if(user == null) {
            return;
        }

        ChestGui gui = category.getChestGui();

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        StaticPane staticPane = new StaticPane(0, 0, 9, 5);

        for(ShopItem shopItem : CategoryManager.getItemsFromCategory(category)) {
            staticPane.addItem(new GuiItem(shopItem.getItemStack(), e -> {

                boolean hasPoints = user.hasPoints(shopItem.getPrice());

                ShopBuyItemEvent event = new ShopBuyItemEvent(player, shopItem.getItemStack(), shopItem, hasPoints);
                EventDispatcher.dispatch(event);

                if(event.isCancelled()) {
                    return;
                }

                if(!hasPoints) {
                    player.closeInventory();
                    player.sendMessage("§cYou don't have enough points to afford this item.");
                    return;
                }

                user.removePoints(shopItem.getPrice());

                if(shopItem.isExecuteCommands()) {
                    List<String> commands = shopItem.getCommandsToExecute().stream().map(it -> it.replace("{player_name}", player.getName())).collect(Collectors.toList());

                    for(String command : commands) {
                        PointsX.getInstance().getServer().dispatchCommand(PointsX.getInstance().getServer().getConsoleSender(), command);
                    }
                }

                if(shopItem.isAddToInventory()) {
                    player.getInventory().addItem(shopItem.getItemStack());
                }

                player.sendMessage("§aYou bought " + shopItem.getItemStack().getAmount() + "x " + shopItem.getItemStack().getType() + " for " + shopItem.getPrice() + " points.");
                player.closeInventory();

            }), shopItem.getX(), shopItem.getY());
        }

        gui.addPane(staticPane);
        gui.show(player);

    }

}
