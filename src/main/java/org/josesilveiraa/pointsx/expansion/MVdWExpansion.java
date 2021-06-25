package org.josesilveiraa.pointsx.expansion;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.manager.UserManager;
import org.josesilveiraa.pointsx.object.User;

public class MVdWExpansion {

    public static void register() {
        PlaceholderAPI.registerPlaceholder(PointsX.getInstance(), "points", event -> {

            Player player = event.getPlayer();

            User user = UserManager.getByUuid(player.getUniqueId());

            if(user != null) {
                return String.valueOf(user.getPoints());
            }
            return null;
        });
    }

}
