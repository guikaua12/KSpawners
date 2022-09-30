package me.approximations.spawners.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@TranslateColors
@ConfigSection(value = "Mensagens")
@ConfigFile(value = "mensagens.yml")
@Getter
@Accessors(fluent = true)
public class MessagesConfig implements ConfigurationInjectable {
    @Getter
    private static final MessagesConfig instance = new MessagesConfig();

    @ConfigField(value = "Spawneradmin")
    private List<String> spawnersAdmin;

    @ConfigField(value = "Jogador-invalido")
    private String jogadorInválido;

    @ConfigField(value = "Spawner-invalido")
    private String spawnerInvalido;

    @ConfigField(value = "Quantia-invalida")
    private String quantiaInvalida;

    @ConfigField(value = "Deu-Spawner")
    private String deuSpawner;

    @ConfigField(value = "Sem-perm-remover")
    private String semPermRemover;

    @ConfigField(value = "Sem-perm-matar")
    private String semPermMatar;

    @ConfigField(value = "Sem-perm-abrir")
    private String semPermAbrir;

    @ConfigField(value = "Sem-perm-adicionar")
    private String semPermAdicionar;

    @ConfigField(value = "Adicionou")
    private String adicionou;

    @ConfigField(value = "Ha-spawners")
    private String haSpawners;

    @ConfigField(value = "Nao-dono")
    private String naoDono;

    @ConfigField(value = "Removeu-amigo")
    private String removeuAmigo;

    @ConfigField(value = "Adicionar-amigo")
    private List<String> adicionarAmigo;

    @ConfigField(value = "Demorou")
    private String demorou;

    @ConfigField(value = "Erro-self")
    private String erroSelf;

    @ConfigField(value = "Erro-amigo-existe")
    private String erroAmigoExiste;

    @ConfigField(value = "Adicionou-amigo")
    private String adicionouAmigo;

    @ConfigField(value = "Erro-vender")
    private String erroVender;

    @ConfigField(value = "Vendeu")
    private String vendeu;



    public static <T> T get(Function<MessagesConfig, T> function) {
        return function.apply(instance);
    }
}
