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
@ConfigFile(value = "menus/amigos.yml")
@Getter
@Accessors(fluent = true)
public class AmigosInventory implements ConfigurationInjectable {
    @Getter
    private static final AmigosInventory instance = new AmigosInventory();

    @ConfigField(value = "Nome", colorize = true)
    private String name;

    @ConfigField(value = "Tamanho", colorize = true)
    private int size;

    @ConfigField(value = "VoltarItem")
    private ConfigurationSection voltarItem;

    @ConfigField(value = "AdicionarAmigo")
    private ConfigurationSection adicionarAmigoItem;

    @ConfigField(value = "Amigo")
    private ConfigurationSection amigoItem;

    public static <T> T get(Function<AmigosInventory, T> function) {
        return function.apply(instance);
    }
}
