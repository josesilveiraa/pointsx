package org.josesilveiraa.pointsx;

import co.aikar.commands.PaperCommandManager;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.josesilveiraa.pointsx.config.Configuration;
import org.josesilveiraa.pointsx.command.PointsCommand;
import org.josesilveiraa.pointsx.expansion.PointsXExpansion;
import org.josesilveiraa.pointsx.listener.PlayerJoinListener;
import org.josesilveiraa.pointsx.listener.PlayerQuitListener;
import org.josesilveiraa.pointsx.manager.StorageManager;
import org.josesilveiraa.pointsx.manager.category.CategoryManager;
import org.josesilveiraa.pointsx.object.Category;
import org.josesilveiraa.pointsx.object.User;
import org.josesilveiraa.pointsx.task.AutoSaveTask;

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
        pluginLogger = getLogger();

        saveDefaultConfig();
        initConfigs();

        initDatabase();
        initCommands();
        initTasks();
        initEvents();
        initCategories();
    }

    private void initExpansions() {
        if(pluginExists("PlaceholderAPI")) {
            new PointsXExpansion().register();
        }
    }

    private void initConfigs() {
        shopConfiguration = new Configuration(getInstance(), "shop.yml");
        shopConfiguration.saveDefaultConfig();
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

    private boolean pluginExists(String pluginName) {
        return pm.getPlugin(pluginName) != null;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        getServer().getScheduler().cancelTasks(getInstance());

        getCache().forEach(((uuid, user) -> user.save()));
        getHikari().close();

        getPluginLogger().log(Level.INFO, "Disabled successfully.");
    }
}
