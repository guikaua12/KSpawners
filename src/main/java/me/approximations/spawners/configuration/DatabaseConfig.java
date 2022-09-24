package me.approximations.spawners.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.function.Function;

@TranslateColors
@ConfigSection(value = "database")
@ConfigFile(value = "config.yml")
@Getter
public class DatabaseConfig implements ConfigurationInjectable {
    @Getter
    private static final DatabaseConfig instance = new DatabaseConfig();

    @ConfigField(value = "type")
    private String type;
    @ConfigField(value = "ip")
    private String ip;
    @ConfigField(value = "db")
    private String db;
    @ConfigField(value = "user")
    private String user;
    @ConfigField(value = "password")
    private String password;
    @ConfigField(value = "table")
    private String table;

    public static <T> T get(Function<DatabaseConfig, T> function) {
        return function.apply(instance);
    }
}
