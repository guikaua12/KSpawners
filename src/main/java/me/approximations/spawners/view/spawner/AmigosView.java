package me.approximations.spawners.view.spawner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.inventory.AmigosInventory;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.*;
import me.saiintbrisson.minecraft.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Arrays;

public class AmigosView extends PaginatedView<Amigo> {
    private final String SPAWNER_CONTEXT_KEY = "spawner";
    public AmigosView() {
        super(AmigosInventory.get(AmigosInventory::size), AmigosInventory.get(AmigosInventory::name));
        setCancelOnClick(true);
        setLayout("XXXXXXXXX",
                  "XOOOOOOOX",
                  "XOOOOOOOX",
                  "XOOOOOOOX",
                  "XOOOOOOOX",
                  "XXXXXXXXX"
        );

        ConfigurationSection voltarItem = AmigosInventory.get(AmigosInventory::voltarItem);

        slot(voltarItem.getInt("Slot"), Utils.getItemFromConfig(voltarItem)).onClick(click -> {
            click.open(MainView.class, click.getData());
        });

    }

    @Override
    protected void onItemRender(PaginatedViewSlotContext<Amigo> render, ViewItem item, Amigo value) {
        Spawner sp = render.get(SPAWNER_CONTEXT_KEY);
        ConfigurationSection amigoItem = AmigosInventory.get(AmigosInventory::amigoItem);
        boolean customHead = amigoItem.getBoolean("CustomHead");
        String headUrl = amigoItem.getString("Head_url");
        boolean playerHead = headUrl.contains("{player}");
        String[] it = amigoItem.getString("Item").split(":");
        String name = amigoItem.getString("Name").replace("{player}", value.getNome());

        item.rendered(() -> {
            if(customHead) {
                if(playerHead) {
                    return new ItemBuilder(value.getNome(), (byte) 0)
                            .setName(name)
                            .setLore(ColorUtil.colored(amigoItem.getStringList("Lore")))
                            .wrap();
                }else {
                    return new ItemBuilder(headUrl)
                            .setName(name)
                            .setLore(ColorUtil.colored(amigoItem.getStringList("Lore")))
                            .wrap();
                }
            }else {
                return new ItemBuilder(TypeUtil.getMaterialFromLegacy(it[0]), Integer.parseInt(it[1]))
                        .setName(name)
                        .setLore(ColorUtil.colored(amigoItem.getStringList("Lore")))
                        .wrap();
            }
        }).onClick(click -> {
            if(!click.getPlayer().getName().equalsIgnoreCase(sp.getDono())) {
                click.getPlayer().sendMessage("§cVocê não é o dono do spawner.");
                return;
            }
            if(click.isLeftClick()) {
                click.open(GerenciarAmigoView.class, ImmutableMap.of(SPAWNER_CONTEXT_KEY, sp, "amigo", value));
            }else if(click.isRightClick()) {
                sp.removeAmigo(value);
                click.getPlayer().sendMessage("§aO jogador §f"+value.getNome()+" §afoi removido como amigo do seu spawner§8.");
                click.update();
            }
        });
    }

    @Override
    protected void onRender(ViewContext context) {
        Spawner sp = context.get(SPAWNER_CONTEXT_KEY);
        context.paginated().setSource(c -> Lists.newArrayList(sp.getAmigos().values()));
        ConfigurationSection adicionarAmigoItem = AmigosInventory.get(AmigosInventory::adicionarAmigoItem);

        context.slot(adicionarAmigoItem.getInt("Slot"),Utils.getItemFromConfig(adicionarAmigoItem)).onClick(click -> {
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
                        Amigo amg = new Amigo(response, false, false, false);
                        sp.addAmigo(amg);
                        p.sendMessage("§aO jogador §f"+response+" §afoi adicionado como amigo ao seu spawner§8.");
                    })
                    .build());
        });
    }
}
