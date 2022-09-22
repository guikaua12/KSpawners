package me.approximations.spawners.view.spawnersShop;

import me.approximations.spawners.Main;
import me.approximations.spawners.manager.ShopManager;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.SpawnerShop;
import me.approximations.spawners.model.User;
import me.approximations.spawners.util.ChatConversationUtils;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.saiintbrisson.minecraft.*;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.Duration;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class SpawnersView extends PaginatedView<SpawnerShop> {
    public SpawnersView() {
        super(6, "Loja de geradores");
        setCancelOnClick(true);
        setLayout("XXXXXXXXX",
                  "XXOOOOOXX",
                  "X<OOOOO>X",
                  "XXOOOOOXX",
                  "XXXXXXXXX",
                  "XXXXXXXXX");
    }

    @Override
    protected void onItemRender(PaginatedViewSlotContext<SpawnerShop> paginatedViewSlotContext, ViewItem viewItem, SpawnerShop spawner) {
        FileConfiguration SPAWNERS_CONFIG = Main.getInstance().getSpawnersConfig().getConfig();
        viewItem.onRender(render -> {
            boolean ativado = SPAWNERS_CONFIG.getBoolean(spawner.getNome()+".ativado");
            render.setItem((ativado ? new ItemBuilder(SPAWNERS_CONFIG.getString(spawner.getNome()+".head"))
                        .setName("§aGerador de "+spawner.getNome())
                        .setLore("",
                                "§fPreço: §7"+ NumberUtils.format(spawner.getValor(), false),
                                "",
                                "  §aDrops:",
                                "  §a▎ §fValor: §2$§7"+Main.getInstance().getSpawnersConfig().getConfig().getString(spawner.getNome()+".drop-valor-unitario"),
                                "",
                                "§fVocê consegue comprar "+NumberUtils.format(Main.getInstance().getEcon().getBalance(Bukkit.getOfflinePlayer(paginatedViewSlotContext.getPlayer().getUniqueId())) / spawner.getValor(), false)+" geradores desse tipo.")
                        .wrap() : new ItemBuilder(Material.BARRIER)
                        .setName("§cGerador bloqueado!")
                        .setLore("§7Esse gerador será desbloqueado"," §7em breve").wrap()));})
                .onClick(click -> {
                    boolean ativado = SPAWNERS_CONFIG.getBoolean(spawner.getNome()+".ativado");
                    if(!ativado) return;
                    click.close();
                    ChatConversationUtils.awaitResponse(click.getPlayer(), ChatConversationUtils.Request.builder()
                                    .messages(Arrays.asList("§aDigite a quantia de geradores que deseja comprar.", "§7Digite §ncancelar§7 para cancelar."))
                                    .timeoutDuration(Duration.ofSeconds(30))
                                    .timeoutWarn("§cVocê demorou para responder.")
                                    .responseConsumer(response -> {
                                        double quantia = NumberUtils.parse(response);
                                        if(quantia < 1) {click.getPlayer().sendMessage("§cDigite uma quantia válida."); return;}
                                        double valor_final = quantia * spawner.getValor();
                                        if(valor_final < 1) {click.getPlayer().sendMessage("§cO valor final não pode ser menor que zero."); return;}
                                        String quantia_formatado = NumberUtils.format(quantia, false);
                                        OfflinePlayer player = Bukkit.getOfflinePlayer(click.getPlayer().getUniqueId());
                                        EconomyResponse er = Main.getInstance().getEcon().withdrawPlayer(player, valor_final);
                                        if(!er.transactionSuccess()) {
                                            click.getPlayer().sendMessage("§cVocê não tem dinheiro o suficiente.");
                                            return;
                                        }
                                        SpawnerManager.giveSpawner(click.getPlayer(), quantia, spawner.getNome());
                                        click.getPlayer().sendMessage("§aVocê comprou §f"+quantia_formatado+"§a spawners de§f "+spawner.getNome()+"§8.");
//                                        click.getPlayer().playSound(click.getPlayer().getEyeLocation(), Sound., 1, 1);
                                        Main.getInstance().getUserDao().insertOrUpdate(new User(player.getName(), quantia));

                                    })
                            .build());
                });


    }

    @Override
    protected void onRender(ViewContext context) {
        context.paginated().setSource(ShopManager.getSpawnerShopList());
        context.slot(49).onRender(render -> {
            render.setItem(new ItemBuilder("293b10155025fc5f39c2bce35690761205f6171eec6857dcbe634cef0686b1ad")
                    .setName("§ePódio")
                    .wrap()
            );
        }).onClick(click -> {
            click.open(TOP10View.class);
        });

//        context.slot(25).onRender(render -> {
////            render.setItem(render.paginated().hasNextPage() ? new ItemBuilder(Material.ARROW).setName("§aPróxima página").toItemStack() : new ItemBuilder(Material.AIR).toItemStack());
////            render.setItem(new ItemBuilder(Material.ARROW).setName("§aPróxima página").toItemStack());
//        }).onClick(click -> {
//            click.paginated().switchToNextPage();
//        });
//
//
//        context.slot(19).onRender(render -> {
////            render.getPlayer().sendMessage(""+context.paginated().getNextPage());
////            render.setItem(render.paginated().hasNextPage() ? new ItemBuilder(Material.ARROW).setName("§cPágina anterior").toItemStack() : new ItemBuilder(Material.AIR).toItemStack());
////            render.setItem(new ItemBuilder(Material.ARROW).setName("§cPágina anterior").toItemStack());
//        }).onClick(click -> {
//            click.paginated().switchToPreviousPage();
//        });


    }

    @Override
    public ViewItem getNextPageItem(PaginatedViewContext<SpawnerShop> context) {
        return item(context.hasNextPage() ? new ItemBuilder(Material.ARROW).setName("§cPágina anterior").wrap() : new ItemBuilder(Material.AIR).wrap());
    }

    @Override
    public ViewItem getPreviousPageItem(PaginatedViewContext<SpawnerShop> context) {
        return item(context.hasPreviousPage() ? new ItemBuilder(Material.ARROW).setName("§aPróxima página").wrap() : new ItemBuilder(Material.AIR).wrap());
    }
}
// §
