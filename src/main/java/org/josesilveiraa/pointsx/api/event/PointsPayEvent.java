package org.josesilveiraa.pointsx.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.pointsx.object.User;

public class PointsPayEvent extends Event implements Cancellable {

    @NotNull
    private static final HandlerList handlers = new HandlerList();

    @Getter @Setter
    private boolean cancelled;

    @Getter private final User senderUser;
    @Getter private final User receiverUser;
    @Getter private final Player player;
    @Getter private final Player receiver;

    public PointsPayEvent(User senderUser, User receiverUser, Player player, Player receiver) {
        this.senderUser = senderUser;
        this.receiverUser = receiverUser;
        this.player = player;
        this.receiver = receiver;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
