package org.josesilveiraa.pointsx.api.event;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ShopOpenEvent extends Event implements Cancellable {

    @Getter @Setter
    private boolean cancelled;

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;

    @Getter
    private final ChestGui chestGui;

    public ShopOpenEvent(Player player, ChestGui chestGui) {
        this.player = player;
        this.chestGui = chestGui;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
