package org.josesilveiraa.points.manager;

import org.josesilveiraa.points.PointsX;
import org.josesilveiraa.points.object.User;

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

}
