package org.josesilveiraa.pointsx.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.api.event.UserSaveEvent;
import org.josesilveiraa.pointsx.manager.EventDispatcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class User {

    private UUID uuid;
    private double points;

    public void removePoints(double points) {
        setPoints(getPoints() - points);
    }

    public void addPoints(double points) {
        setPoints(getPoints() + points);
    }

    public boolean hasPoints(double points) {
        return getPoints() >= points;
    }

    public void load() {
        PointsX.getCache().put(getUuid(), this);
    }

    public void save() {
        try(Connection connection = PointsX.getHikari().getConnection()) {

            PreparedStatement st = connection.prepareStatement("UPDATE users SET points = ? WHERE uuid = ?");

            st.setDouble(1, getPoints());
            st.setString(2, getUuid().toString());

            st.executeUpdate();
            st.close();

            UserSaveEvent event = new UserSaveEvent(this);
            EventDispatcher.dispatch(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
