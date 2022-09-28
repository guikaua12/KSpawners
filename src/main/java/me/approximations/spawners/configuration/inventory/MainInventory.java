package me.approximations.spawners.configuration.inventory;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.approximations.spawners.configuration.DatabaseConfig;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@TranslateColors
@ConfigSection(value = "")
@ConfigFile(value = "menus/principal.yml")
@Getter
@Accessors(fluent = true)
public class MainInventory implements ConfigurationInjectable {
    @Getter
    private static final MainInventory instance = new MainInventory();

    @ConfigField(value = "Nome", colorize = true)
    private String name;

    @ConfigField(value = "Tamanho", colorize = true)
    private int size;

    @ConfigField(value = "Info")
    private ConfigurationSection infoItem;

    @ConfigField(value = "Drops")
    private ConfigurationSection dropsItem;

    @ConfigField(value = "Amigos")
    private ConfigurationSection AmigosItem;

    public static <T> T get(Function<MainInventory, T> function) {
        return function.apply(instance);
    }
}
