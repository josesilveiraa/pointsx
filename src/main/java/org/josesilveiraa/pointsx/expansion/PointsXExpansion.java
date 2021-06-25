package org.josesilveiraa.pointsx.expansion;

import com.lgou2w.ldk.bukkit.depend.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.josesilveiraa.pointsx.manager.UserManager;
import org.josesilveiraa.pointsx.object.User;

public class PointsXExpansion extends PlaceholderExpansion {

    @Nullable
    @Override
    public String getAuthor() {
        return "Josesilveiraa";
    }

    @Nullable
    @Override
    public String getIdentifier() {
        return "pointsx";
    }

    @Nullable
    @Override
    public String getRequiredPlugin() {
        return "PointsX";
    }

    @Nullable
    @Override
    public String getVersion() {
        return "1.0";
    }

    @Nullable
    @Override
    public String onPlaceholderRequest(@Nullable Player player, @NotNull String s) {
        if(player == null) return "";

        // %pointsx_points%
        if(s.equals("points")) {
            User user = UserManager.getByUuid(player.getUniqueId());

            if(user != null) {
                return String.valueOf(user.getPoints());
            }
        }
        return null;
    }
}
