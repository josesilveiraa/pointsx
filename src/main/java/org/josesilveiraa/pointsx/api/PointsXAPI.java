package org.josesilveiraa.pointsx.api;

import org.bukkit.entity.Player;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.manager.UserManager;
import org.josesilveiraa.pointsx.object.User;
import org.josesilveiraa.pointsx.task.AutoSaveTask;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class PointsXAPI {

    @Nullable
    public User getUser(UUID uuid) {
        return UserManager.getByUuid(uuid);
    }

    @Nullable
    public User getUser(Player player) {
        return UserManager.getByPlayer(player);
    }

    @Nullable
    public User getUser(String playerName) {
        Player player = PointsX.getInstance().getServer().getPlayer(playerName);

        return player == null ? null : UserManager.getByPlayer(player);
    }

    public Collection<User> getAllUsers() {
        return PointsX.getCache().values();
    }

    public void runAutoSaveTask() {
        new AutoSaveTask().runTask(PointsX.getInstance());
    }

}
