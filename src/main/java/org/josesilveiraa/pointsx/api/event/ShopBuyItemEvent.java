package org.josesilveiraa.pointsx.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.pointsx.object.ShopItem;

public class ShopBuyItemEvent extends Event implements Cancellable {

    @Getter @Setter
    private boolean cancelled;

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;

    @Getter
    private final ItemStack itemStack;

    @Getter
    private final ShopItem shopItem;

    @Getter
    private final boolean bought;

    public ShopBuyItemEvent(Player player, ItemStack itemStack, ShopItem shopItem, boolean bought) {
        this.player = player;
        this.itemStack = itemStack;
        this.shopItem = shopItem;
        this.bought = bought;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
