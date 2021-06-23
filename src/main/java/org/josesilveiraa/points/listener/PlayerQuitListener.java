package org.josesilveiraa.points.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.josesilveiraa.points.manager.UserManager;
import org.josesilveiraa.points.object.User;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e) {
        User user = UserManager.getByUuid(e.getPlayer().getUniqueId());

        if(user != null) {
            user.save();
        }
    }

}
