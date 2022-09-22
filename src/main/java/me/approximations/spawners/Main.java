package me.approximations.spawners;

import com.jaoow.sql.executor.SQLExecutor;
import lombok.Getter;
import me.approximations.spawners.command.SpawnersCommand;
import me.approximations.spawners.config.MConfig;
import me.approximations.spawners.dao.SQLProvider;
import me.approximations.spawners.dao.UserDao;
import me.approximations.spawners.dao.adapter.UserAdapter;
import me.approximations.spawners.dao.repository.UserRepository;
import me.approximations.spawners.listener.*;
import me.approximations.spawners.model.User;
import me.approximations.spawners.util.ChatConversationUtils;
import me.approximations.spawners.util.ConfigReader;
import me.approximations.spawners.view.spawner.AmigosView;
import me.approximations.spawners.view.spawner.DropsView;
import me.approximations.spawners.view.spawner.GerenciarAmigoView;
import me.approximations.spawners.view.spawner.MainView;
import me.approximations.spawners.view.spawnersShop.SpawnersView;
import me.approximations.spawners.view.spawnersShop.TOP10View;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.ViewFrame;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    @Getter
    private static Main instance;
    @Getter
    private ConfigReader spawnersConfig;
    @Getter
    private ViewFrame viewFrame;
    @Getter
    private BukkitFrame bukkitFrame;
    @Getter
    private Economy econ;
    @Getter
    private SQLExecutor sqlExecutor;
    @Getter
    private UserRepository userRepository;
    @Getter
    private UserDao userDao;
    @Getter
    private MConfig mconfig;


    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        spawnersConfig = new ConfigReader(this, "", "spawners.yml");
        spawnersConfig.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();
        setupDatabase();
        ChatConversationUtils.scheduleTimeoutRunnable();
        setupListener();
        setupCommand(this);
        setupView(this);
        setupShop();

        Bukkit.getServer().getScheduler().runTaskLater(this, () -> {
            if(!setupEconomy()) {
                this.getLogger().log(Level.SEVERE, "Vault não encontrado, desabilitando plugin.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }, 150L);
    }

    private void setupConfig() {
        mconfig = new MConfig(
                getConfig().getString("database.type"),
                getConfig().getString("database.ip"),
                getConfig().getString("database.db"),
                getConfig().getString("database.user"),
                getConfig().getString("database.password"),
                getConfig().getString("database.table")
        );
    }

    private void setupListener() {
        Bukkit.getPluginManager().registerEvents(new PlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(), this);
        Bukkit.getPluginManager().registerEvents(new PreventItemClean(), this);
        Bukkit.getPluginManager().registerEvents(new MenuOpenListener(), this);
        ChatConversationUtils.registerListener();
    }

    private void setupView(Plugin plugin) {
        viewFrame = new ViewFrame(plugin);
        viewFrame.register(
                new SpawnersView(),
                new MainView(),
                new DropsView(),
                new AmigosView(),
                new GerenciarAmigoView(),
                new TOP10View()
        );
    }

    private void setupCommand(Plugin plugin) {
        bukkitFrame = new BukkitFrame(plugin);
        bukkitFrame.registerCommands(new SpawnersCommand());
    }

    private void setupShop() {
//        FileConfiguration spawnersConfig = this.getSpawnersConfig().getConfig();
//        for (String key : spawnersConfig.getKeys(false)) {
//            ConfigurationSection sec = spawnersConfig.getConfigurationSection(key);
//            SpawnerShop.builder()
//                    .nome(key)
//                    .valor(NumberUtils.parse(spawnersConfig.getString(key+".spawner-valor")))
//                    .build()
//            ShopManager.addSpawnerShop(new SpawnerShop(key, NumberUtils.parse(spawnersConfig.getString(key+".spawner-valor"))));
//        }
    }

    private void setupDatabase() {
        sqlExecutor = new SQLProvider(this).setupDatabase();
        UserAdapter ua = new UserAdapter();
        sqlExecutor.registerAdapter(User.class, ua);
        userRepository = new UserRepository(this, sqlExecutor);
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}