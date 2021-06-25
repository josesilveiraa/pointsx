package org.josesilveiraa.pointsx.manager;

import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StorageManager {

    public static void loadUsers() {
        try(Connection connection = PointsX.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                double points = rs.getDouble("points");
                User toAdd = new User(uuid, points);

                toAdd.load();
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean has(UUID uuid) {
        try(Connection connection = PointsX.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT uuid FROM users WHERE uuid = ?");
            st.setString(1, uuid.toString());

            ResultSet rs = st.executeQuery();
            boolean result = rs.next();

            rs.close();
            st.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insert(User user) {
        try(Connection connection = PointsX.getHikari().getConnection()) {

            PreparedStatement st = connection.prepareStatement("INSERT INTO users (uuid, points) VALUES (?, ?)");
            st.setString(1, user.getUuid().toString());
            st.setDouble(2, user.getPoints());
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String tableName, String columns) {
        try(Connection connection = PointsX.getHikari().getConnection()) {

            PreparedStatement st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + tableName + "` (" + columns + ")");
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
