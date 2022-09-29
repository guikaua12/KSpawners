package me.approximations.spawners;

import com.jaoow.sql.executor.SQLExecutor;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import me.approximations.spawners.command.SpawnersCommand;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.configuration.register.ConfigurationRegister;
import me.approximations.spawners.dao.SQLProvider;
import me.approximations.spawners.dao.UserDao;
import me.approximations.spawners.dao.adapter.UserAdapter;
import me.approximations.spawners.dao.repository.UserRepository;
import me.approximations.spawners.dao.scheduler.AutoSave;
import me.approximations.spawners.listener.*;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.model.User;
import me.approximations.spawners.util.ChatConversationUtils;
import me.approximations.spawners.util.NBTEditor;
import me.approximations.spawners.versions.SpawnerManager_1_14;
import me.approximations.spawners.versions.SpawnerManager_1_8_1_13;
import me.approximations.spawners.view.spawner.AmigosView;
import me.approximations.spawners.view.spawner.DropsView;
import me.approximations.spawners.view.spawner.GerenciarAmigoView;
import me.approximations.spawners.view.spawner.MainView;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.ViewFrame;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    @Getter
    private static Main instance;
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
    private SpawnerManager spawnerManager;


    @Override
    public void onLoad() {
        List<Integer> a = new ArrayList<Integer>();
        this.saveDefaultConfig();
        setupConfig();
        if(NBTEditor.getMinecraftVersion().greaterThanOrEqualTo(NBTEditor.MinecraftVersion.v1_14)) {
            spawnerManager = new SpawnerManager_1_14();
        }else {
            NBTInjector.inject();
            spawnerManager = new SpawnerManager_1_8_1_13();
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getServer().getScheduler().runTaskLater(this, () -> {
            if(!setupEconomy()) {
                this.getLogger().log(Level.SEVERE, "Vault não encontrado, desabilitando plugin.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }, 150L);

        setupDatabase();
        ChatConversationUtils.scheduleTimeoutRunnable();
        setupListener();
        setupCommand(this);
        setupView();
        setupSpawners();
    }

    private void setupConfig() {
        new ConfigurationRegister(this).register();
    }

    private void setupListener() {
        Bukkit.getPluginManager().registerEvents(new PlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuOpenListener(), this);
        ChatConversationUtils.registerListener();
    }

    private void setupView() {
        viewFrame = ViewFrame.of(this,
                new MainView(),
                new DropsView(this),
                new AmigosView(),
                new GerenciarAmigoView()
        ).register();
    }

    private void setupCommand(Plugin plugin) {
        bukkitFrame = new BukkitFrame(plugin);
        bukkitFrame.registerCommands(new SpawnersCommand());
    }

    private void setupSpawners() {
        ConfigurationSection s = SpawnersConfig.get(SpawnersConfig::getSpawners);
        for (String key : s.getKeys(false)) {
            //
            ConfigurationSection spsection = s.getConfigurationSection(key);
            EntityType entity = EntityType.valueOf(spsection.getString("Entity"));
            String mobName = spsection.getString("MobName");
            double dropPrice = spsection.getDouble("DropPrice");

            // adicionar na map
            SpawnerWrapper sw = SpawnerWrapper.builder()
                    .key(key)
                    .entityType(entity)
                    .mobName(mobName)
                    .dropPrice(dropPrice)
                    .build();
            spawnerManager.insertSpawnerWrapper(sw);
        }
    }

    private void setupDatabase() {
        SQLProvider sqlProvider = new SQLProvider(this);
        sqlExecutor = new SQLProvider(this).setupDatabase();
        UserAdapter ua = new UserAdapter();
        sqlExecutor.registerAdapter(User.class, ua);
        userRepository = new UserRepository(this, sqlExecutor);
        userRepository.createTable();

        userDao = new UserDao(userRepository, this);

        sqlProvider.registerEvents();

        AutoSave autoSave = new AutoSave(this);
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