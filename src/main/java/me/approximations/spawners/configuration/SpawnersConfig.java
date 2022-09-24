package me.approximations.spawners.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@TranslateColors
@ConfigFile(value = "spawners.yml")
@Getter
public class SpawnersConfig implements ConfigurationInjectable {
    @Getter
    private static final SpawnersConfig instance = new SpawnersConfig();

    @ConfigSection(value = "Spawners")
    private ConfigurationSection spawners;

    public static <T> T get(Function<SpawnersConfig, T> function) {
        return function.apply(instance);
    }
}
