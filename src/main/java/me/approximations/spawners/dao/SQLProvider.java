package me.approximations.spawners.dao;

import com.jaoow.sql.executor.SQLExecutor;
import me.approximations.spawners.Main;
import me.approximations.spawners.config.MConfig;
import org.bukkit.configuration.file.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import java.sql.*;
import com.jaoow.sql.connector.type.impl.*;
import java.io.*;

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
        MConfig CONFIG = plugin.getMconfig();
        this.IP = CONFIG.getDbIp();
        this.DB = CONFIG.getDbDb();
        this.USER = CONFIG.getDbUser();
        this.PASSWORD = CONFIG.getDbPassword();
        this.TYPE = CONFIG.getDbType();
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
}
