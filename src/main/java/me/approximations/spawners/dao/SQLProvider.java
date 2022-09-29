package me.approximations.spawners.dao;

import com.jaoow.sql.connector.type.impl.MySQLDatabaseType;
import com.jaoow.sql.connector.type.impl.SQLiteDatabaseType;
import com.jaoow.sql.executor.SQLExecutor;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.DatabaseConfig;
import me.approximations.spawners.dao.listener.JoinLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.SQLException;

public class SQLProvider
{
    private final Main plugin;
    private final String IP;
    private final String DB;
    private final String USER;
    private final String PASSWORD;
    private final String TYPE;
    
    public SQLProvider(final Main plugin) {
        this.plugin = plugin;
        this.IP = DatabaseConfig.get(DatabaseConfig::getIp);
        this.DB = DatabaseConfig.get(DatabaseConfig::getDb);
        this.USER = DatabaseConfig.get(DatabaseConfig::getUser);
        this.PASSWORD = DatabaseConfig.get(DatabaseConfig::getPassword);
        this.TYPE = DatabaseConfig.get(DatabaseConfig::getType);
    }
    
    public SQLExecutor setupDatabase() {
        if (this.TYPE.equalsIgnoreCase("mysql")) {
            final MySQLDatabaseType mysql = MySQLDatabaseType.builder().address(this.IP).database(this.DB).username(this.USER).password(this.PASSWORD).build();
            try {
                return new SQLExecutor(mysql.connect());
            }
            catch (SQLException e) {
                this.plugin.getLogger().severe("Não foi possível conectar ao MySQL, desabilitando plugin.");
                Bukkit.getPluginManager().disablePlugin((Plugin)this.plugin);
                e.printStackTrace();
                return null;
            }
        }
        if (this.TYPE.equalsIgnoreCase("sqlite")) {
            final SQLiteDatabaseType sqlite = SQLiteDatabaseType.builder().file(new File(new File(this.plugin.getDataFolder(), ""), "database.db")).build();
            try {
                return new SQLExecutor(sqlite.connect());
            }
            catch (SQLException e) {
                this.plugin.getLogger().severe("Não foi possível conectar ao MySQL, desabilitando plugin.");
                Bukkit.getPluginManager().disablePlugin((Plugin)this.plugin);
                e.printStackTrace();
            }
        }
        return null;
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinLeaveEvent(), plugin);
    }
}
