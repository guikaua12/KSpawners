package me.approximations.spawners.configuration.inventory;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@TranslateColors
@ConfigSection(value = "")
@ConfigFile(value = "menus/drops.yml")
@Getter
@Accessors(fluent = true)
public class DropsInventory implements ConfigurationInjectable {
    @Getter
    private static final DropsInventory instance = new DropsInventory();

    @ConfigField(value = "Nome", colorize = true)
    private String name;

    @ConfigField(value = "Tamanho", colorize = true)
    private int size;

    @ConfigField(value = "Drop_vazio")
    private ConfigurationSection vazioItem;

    @ConfigField(value = "Drop")
    private ConfigurationSection dropItem;

    public static <T> T get(Function<DropsInventory, T> function) {
        return function.apply(instance);
    }
}
