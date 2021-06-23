package org.josesilveiraa.points.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.points.object.User;

public class PointsSetEvent extends Event implements Cancellable {

    @NotNull @Getter
    private static final HandlerList handlers = new HandlerList();

    @Getter private final CommandSender sender;
    @Getter private final User receiverUser;
    @Getter private final Player receiver;

    @Getter @Setter
    private boolean cancelled;

    public PointsSetEvent(CommandSender sender, User receiverUser, Player receiver) {
        this.sender = sender;
        this.receiverUser = receiverUser;
        this.receiver = receiver;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
