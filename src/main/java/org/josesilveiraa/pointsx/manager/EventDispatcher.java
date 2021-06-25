package org.josesilveiraa.pointsx.manager;

import org.bukkit.event.Event;
import org.josesilveiraa.pointsx.PointsX;

public class EventDispatcher {

    public static void dispatch(Event event) {
        PointsX.getInstance().getServer().getPluginManager().callEvent(event);
    }

}
