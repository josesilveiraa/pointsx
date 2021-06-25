package org.josesilveiraa.pointsx.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.pointsx.object.User;

public class UserSaveEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter @Setter
    private boolean cancelled;

    @Getter
    private final User user;

    public UserSaveEvent(User user) {
        this.user = user;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
