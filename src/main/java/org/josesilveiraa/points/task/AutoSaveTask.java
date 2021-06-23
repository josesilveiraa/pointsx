package org.josesilveiraa.points.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.points.PointsX;

public class AutoSaveTask extends BukkitRunnable {
    @Override
    public void run() {
        PointsX.getCache().forEach(((uuid, user) -> user.save()));
    }
}
