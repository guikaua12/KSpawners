package me.approximations.spawners.view.spawner;


import com.google.common.collect.ImmutableMap;
import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.LocationSerializer;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MainView extends View {
    private static FileConfiguration CONFIG = Main.getInstance().getSpawnersConfig().getConfig();

    public MainView() {
        super(3, "Gerador");
        setCancelOnClick(true);
    }

    @Override
    protected void onRender(ViewContext context) {
        Spawner sp = getSpawner(context);
        slot(11, new ItemBuilder(CONFIG.getString(sp.getNome()+".head"))
                .setName("§a"+sp.getNome())
                .setLore("§fQuantia: §7"+ NumberUtils.format(sp.getQuantia(), false),
                        "",
                        "  §aValores§7:",
                        "§8▪ §fQuantia de drops: §7"+NumberUtils.format(sp.getDrops(), false),
                        "§8▪ §fValor total dos drops: "+("§2$§7"+NumberUtils.format(sp.getDropsValorTotal(), false)),
                        "",
                        "§fProprietário: §7"+ Bukkit.getOfflinePlayer(sp.getDono()).getName()
                )
                .wrap()
        );

        slot(13, new ItemBuilder("e857766cccf167e41dc2a88b7203be3fb36cc8ff928536127b01875f6a4edbb6")
                .setName("§aDrops armazenados")
                .setLore("§7Veja os drops armazenados", "§7dentro deste gerador")
                .wrap()
        ).onClick(click -> click.open(DropsView.class, ImmutableMap.of("spawner", getSpawner(context))));

        slot(14, new ItemBuilder(Material.COMPASS)
                .setName("§aPermissões")
                .setLore("§7Ajuste as permissões do", "§7seu gerador").wrap()
        ).onClick(click -> {
            click.open(AmigosView.class, new HashMap<String, Object>() {{put("spawner", getSpawner(context));}});
        });

        slot(15, new ItemBuilder(Material.BARRIER)
                .setName("§cRemover spawner")
                .wrap()
        ).onClick(click -> {
            Player player = click.getPlayer();
            Spawner sp2 = getSpawner(click);
            Location spl = sp2.getLocation();
            if(!player.hasPermission("spawners.admin")) {
                if(!player.getName().equalsIgnoreCase(sp.getDono())) {
                    Amigo amigo = sp2.getAmigoByName(player.getName());
                    if(amigo == null) {
                        player.sendMessage("§cVocê não tem permissão para remover geradores.");
                        return;
                    }
                    if(!amigo.isCanBreak()) {
                        player.sendMessage("§cVocê não tem permissão para remover geradores.");
                        return;
                    }
                }
            }

            String nome = sp2.getNome();
            Double quantia = sp2.getQuantia();
            SpawnerManager.giveSpawner(player, quantia, nome);
            Block block = spl.getBlock();
            block.setType(Material.AIR);
            click.close();





            for (Entity entity : block.getWorld().getNearbyEntities(block.getLocation(), 5.0, 5.0, 5.0)) {
                if(!entity.getType().equals(EntityType.PLAYER)) {
                    if(entity.hasMetadata("LOCATION") && LocationSerializer.getInstance().encode(block.getLocation()).equalsIgnoreCase(entity.getMetadata("LOCATION").get(0).asString())) {
                        entity.remove();
                        if(entity.getPassenger() != null) {
                            entity.getPassenger().remove();
                        }
                    }
                }
            }
        });

    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return SpawnerManager.getSpawner(sp.getLocation());
    }

}
