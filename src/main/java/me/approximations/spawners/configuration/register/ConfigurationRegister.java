package me.approximations.spawners.configuration.register;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.RequiredArgsConstructor;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.DatabaseConfig;
import me.approximations.spawners.configuration.SpawnersConfig;

@RequiredArgsConstructor
public class ConfigurationRegister {
    private final Main plugin;

    public void register() {
        BukkitConfigurationInjector bukkitConfigurationInjector = new BukkitConfigurationInjector(plugin);

        bukkitConfigurationInjector.saveDefaultConfiguration(plugin, "spawners.yml");

        bukkitConfigurationInjector.injectConfiguration(DatabaseConfig.getInstance(), SpawnersConfig.getInstance());
    }

}
