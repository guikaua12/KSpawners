package me.approximations.spawners.configuration.register;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.RequiredArgsConstructor;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.DatabaseConfig;
import me.approximations.spawners.configuration.MessagesConfig;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.configuration.inventory.AmigosInventory;
import me.approximations.spawners.configuration.inventory.DropsInventory;
import me.approximations.spawners.configuration.inventory.GerenciarAmigosInventory;
import me.approximations.spawners.configuration.inventory.MainInventory;

@RequiredArgsConstructor
public class ConfigurationRegister {
    private final Main plugin;

    public void register() {
        BukkitConfigurationInjector bukkitConfigurationInjector = new BukkitConfigurationInjector(plugin);

        bukkitConfigurationInjector.saveDefaultConfiguration(plugin,
                "spawners.yml",
                "menus/principal.yml",
                "menus/drops.yml",
                "menus/amigos.yml",
                "menus/gerenciar_amigos.yml",
                "mensagens.yml"
        );

        bukkitConfigurationInjector.injectConfiguration(
                DatabaseConfig.getInstance(),
                SpawnersConfig.getInstance(),
                MainInventory.instance(),
                DropsInventory.instance(),
                AmigosInventory.instance(),
                GerenciarAmigosInventory.instance(),
                MessagesConfig.instance()
        );
    }

}
