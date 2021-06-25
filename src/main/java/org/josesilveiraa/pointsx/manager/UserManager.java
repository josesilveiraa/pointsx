package org.josesilveiraa.pointsx.manager;

import org.bukkit.entity.Player;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.object.User;

import javax.annotation.Nullable;
import java.util.UUID;

public class UserManager {

    @Nullable
    public static User getByUuid(UUID uuid) {
        if(PointsX.getCache().containsKey(uuid)) {
            return PointsX.getCache().get(uuid);
        }
        return null;
    }

    @Nullable
    public static User getByPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        return getByUuid(uuid);
    }

}
