package me.approximations.spawners.view.spawner;

import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.ChatConversationUtils;
import me.approximations.spawners.util.ItemBuilder;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Arrays;

public class AmigosView extends PaginatedView<Amigo> {
    public AmigosView() {
        super(6, "Permissões");
        setCancelOnClick(true);
        setLayout("XXXXXXXXX",
                  "XOOOOOOOX",
                  "XOOOOOOOX",
                  "XOOOOOOOX",
                  "XOOOOOOOX",
                  "XXXXXXXXX"
        );
        slot(49, new ItemBuilder(Material.ARROW)
                .setName("§cVoltar")
                .wrap()
        ).onClick(click -> {
            click.open(MainView.class, click.getData());
        });
    }

    @Override
    protected void onItemRender(PaginatedViewSlotContext<Amigo> render, ViewItem item, Amigo value) {
        item.onRender(r -> {
            r.setItem(new ItemBuilder(value.getNome(), (byte) 0)
                    .setName("§a"+value.getNome())
                    .setLore("§7Clique com o botão ", "§7ESQUERDO para gerenciar.",
                        "",
                        "§7Clique com o botão ", "§7DIREITO para remover.").wrap()
            );
        }).onClick(click -> {
            Spawner sp = getSpawner(click);
            if(!click.getPlayer().getName().equalsIgnoreCase(sp.getDono())) {
                click.getPlayer().sendMessage("§cVocê não é o dono do spawner.");
                return;
            }
            if(click.getClickOrigin().isLeftClick()) {
                click.open(GerenciarAmigoView.class, click.getData());
            }else if(click.getClickOrigin().isRightClick()) {
                sp.removeAmigo(value);
                click.getPlayer().sendMessage("§aO jogador §f"+value.getNome()+" §afoi removido como amigo do seu spawner§8.");
                click.update();
            }
        });
    }

    @Override
    protected void onRender(ViewContext context) {
        context.update();

        slot(48, new ItemBuilder("b056bc1244fcff99344f12aba42ac23fee6ef6e3351d27d273c1572531f")
                .setName("§aAdicionar jogador")
                .setLore("§7Adicione jogadores ao", "§7seu spawner e gerencie", "§7suas permissões.")
                .wrap()
        ).onClick(click -> {
            Spawner sp = getSpawner(context);
            Player p = click.getPlayer();
            if(!click.getPlayer().hasPermission("spawners.admin")) {
                if(!click.getPlayer().getName().equalsIgnoreCase(sp.getDono())) {
                    click.getPlayer().sendMessage("§cVocê não é o dono do spawner.");
                    return;
                }
            }
            click.close();
            ChatConversationUtils.awaitResponse(click.getPlayer(), ChatConversationUtils.Request.builder()
                    .messages(Arrays.asList("§aDigite o nick de um jogador.", "§7Digite §ncancelar§7 para cancelar."))
                    .timeoutDuration(Duration.ofSeconds(30))
                    .timeoutWarn("§cVocê demorou para responder.")
                    .responseConsumer(response -> {
                        if(response.equalsIgnoreCase(p.getName())) {
                            p.sendMessage("§cVocê não pode adicionar a si mesmo.");
                            return;
                        }
                        if(sp.getAmigoByName(response) != null) {
                            p.sendMessage("§cEsse jogador já está no spawner.");
                            return;
                        }
                        OfflinePlayer player = Bukkit.getOfflinePlayer(response);
                        if(player == null) {
                            p.sendMessage("§cJogador não encontrado.");
                            return;
                        }
                        if(!player.hasPlayedBefore()) {
                            p.sendMessage("§cJogador não encontrado.");
                            return;
                        }
                        sp.addAmigo(new Amigo(response, false, false, false));
                        p.sendMessage("§aO jogador §f"+response+" §afoi adicionado como amigo ao seu spawner§8.");
                    })
                    .build());
        });
    }

    @Override
    protected void onUpdate(@NotNull ViewContext context) {
        Spawner sp = getSpawner(context);
        context.paginated().setSource(sp.getAmigos());



    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return SpawnerManager.getSpawner(sp.getLocation());
    }
}
