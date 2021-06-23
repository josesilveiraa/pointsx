package org.josesilveiraa.points;

import co.aikar.commands.PaperCommandManager;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.josesilveiraa.points.config.Configuration;
import org.josesilveiraa.points.command.PointsCommand;
import org.josesilveiraa.points.listener.PlayerJoinListener;
import org.josesilveiraa.points.listener.PlayerQuitListener;
import org.josesilveiraa.points.manager.StorageManager;
import org.josesilveiraa.points.manager.category.CategoryManager;
import org.josesilveiraa.points.object.Category;
import org.josesilveiraa.points.object.User;
import org.josesilveiraa.points.task.AutoSaveTask;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PointsX extends JavaPlugin {

    @Getter private static PointsX instance;

    @Getter private static HikariDataSource hikari;

    @Getter private static Logger pluginLogger;

    @Getter private PaperCommandManager commandManager;

    @Getter private static final HashMap<UUID, User> cache = new HashMap<>();

    @Getter private static final HashMap<String, Category> categories = new HashMap<>();

    @Getter private static Configuration shopConfiguration;

    private final PluginManager pm = getServer().getPluginManager();

    @Override
    public void onEnable() {
        instance = this;
        init();
        getPluginLogger().log(Level.INFO, "Enabled successfully.");
    }

    private void init() {
        pluginLogger = getPluginLogger();

        saveDefaultConfig();
        initConfigs();

        initDatabase();
        initCommands();
        initTasks();
        initEvents();
        initCategories();
    }

    private void initConfigs() {
        shopConfiguration = new Configuration(getInstance(), "shop.yml");
    }

    private void initEvents() {
        pm.registerEvents(new PlayerJoinListener(), getInstance());
        pm.registerEvents(new PlayerQuitListener(), getInstance());
    }

    private void initCommands() {
        commandManager = new PaperCommandManager(getInstance());
        commandManager.registerCommand(new PointsCommand());
        commandManager.enableUnstableAPI("help");
    }

    private void initCategories() {
        CategoryManager.loadCategories();
    }

    private void initTasks() {
        new AutoSaveTask().runTaskTimerAsynchronously(getInstance(), 12000L, 12000L);
    }

    private void initDatabase() {
        hikari = new HikariDataSource();

        hikari.setMaximumPoolSize(10);
        String host = getConfig().getString("mysql.host");
        String user = getConfig().getString("mysql.user");
        String password = getConfig().getString("mysql.password");
        String database = getConfig().getString("mysql.database");
        int port = getConfig().getInt("mysql.port");

        hikari.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikari.setUsername(user);
        hikari.setPassword(password);
        hikari.addDataSourceProperty("cachePrepStmts", "true");
        hikari.addDataSourceProperty("prepStmtCacheSize", "250");
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        StorageManager.createTable("users", "uuid varchar(36), points double");
        StorageManager.loadUsers();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        getServer().getScheduler().cancelTasks(getInstance());

        getPluginLogger().log(Level.INFO, "Disabled successfully.");
    }
}
