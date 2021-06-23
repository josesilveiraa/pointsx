package org.josesilveiraa.points.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.josesilveiraa.points.object.User;

public class PointsRemoveEvent extends Event implements Cancellable {

    @Getter private static final HandlerList handlers = new HandlerList();

    @Getter @Setter private boolean cancelled;

    @Getter private final CommandSender sender;
    @Getter private final Player receiver;
    @Getter private final User receiverUser;

    public PointsRemoveEvent(CommandSender sender, Player receiver, User receiverUser) {
        this.sender = sender;
        this.receiver = receiver;
        this.receiverUser = receiverUser;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
