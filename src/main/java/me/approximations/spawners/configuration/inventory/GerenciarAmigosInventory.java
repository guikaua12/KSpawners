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
@ConfigFile(value = "menus/gerenciar_amigos.yml")
@Getter
@Accessors(fluent = true)
public class GerenciarAmigosInventory implements ConfigurationInjectable {
    @Getter
    private static final GerenciarAmigosInventory instance = new GerenciarAmigosInventory();

    @ConfigField(value = "Nome", colorize = true)
    private String name;

    @ConfigField(value = "Tamanho", colorize = true)
    private int size;

    @ConfigField(value = "AdicionarSlot", colorize = true)
    private int adicionarSlot;

    @ConfigField(value = "RemoverSlot", colorize = true)
    private int removerSlot;

    @ConfigField(value = "MatarSlot", colorize = true)
    private int matarSlot;

    @ConfigField(value = "VoltarItem")
    private ConfigurationSection voltarItem;

    @ConfigField(value = "AdicionarOn")
    private ConfigurationSection adicionarOn;
    @ConfigField(value = "AdicionarOff")
    private ConfigurationSection adicionarOff;

    @ConfigField(value = "RemoverOn")
    private ConfigurationSection removerOn;
    @ConfigField(value = "RemoverOff")
    private ConfigurationSection removerOff;

    @ConfigField(value = "MatarOn")
    private ConfigurationSection matarOn;
    @ConfigField(value = "MatarOff")
    private ConfigurationSection matarOff;

    public static <T> T get(Function<GerenciarAmigosInventory, T> function) {
        return function.apply(instance);
    }
}
