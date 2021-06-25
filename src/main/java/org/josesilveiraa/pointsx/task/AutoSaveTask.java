package org.josesilveiraa.pointsx.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.pointsx.PointsX;

import java.util.logging.Level;

public class AutoSaveTask extends BukkitRunnable {
    @Override
    public void run() {
        PointsX.getCache().forEach(((uuid, user) -> user.save()));
        PointsX.getPluginLogger().log(Level.INFO, "Executing auto-save...");
    }
}
