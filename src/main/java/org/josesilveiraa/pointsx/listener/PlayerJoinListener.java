package org.josesilveiraa.pointsx.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.josesilveiraa.pointsx.manager.StorageManager;
import org.josesilveiraa.pointsx.object.User;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if(!StorageManager.has(uuid)) {
            User user = new User(uuid, 0.0);
            user.load();
            StorageManager.insert(user);
        }
    }

}
