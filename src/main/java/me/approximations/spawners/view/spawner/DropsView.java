package me.approximations.spawners.view.spawner;

import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DropsView extends View {
    private static final FileConfiguration CONFIG = Main.getInstance().getSpawnersConfig().getConfig();
    public DropsView() {
        super(4, "Drops");
        setCancelOnClick(true);

        slot(31, new ItemBuilder(Material.ARROW)
                .setName("§cVoltar")
                .wrap()
        ).onClick(click -> {
            click.open(MainView.class, click.getData());
        });
    }

    @Override
    protected void onRender(ViewContext context) {
        context.update();
    }

    @Override
    protected void onUpdate(@NotNull ViewContext context) {
        slot(13).onRender(render -> {
            Spawner sp = getSpawner(render);
            String item = CONFIG.getString(sp.getNome()+".drop-item");
            render.setItem(sp.getDrops() < 1 ? new ItemBuilder(TypeUtil.convertFromLegacy("WEB", 0)).setName("§cVazio").wrap() : new ItemBuilder(TypeUtil.convertFromLegacy(item, 0)).setName("§aDrop de "+sp.getNome()).setLore("",
                    "§fQuantia: §7"+ NumberUtils.format(sp.getDrops(), false),
                    "§fValor ao vender: §7"+NumberUtils.format(sp.getDrops() * sp.getDropValorUnitario(), false)).wrap());
        }).onClick(click -> {
            Spawner sp = getSpawner(click);
            EconomyResponse er = Main.getInstance().getEcon().depositPlayer(click.getPlayer(), sp.getDropsValorTotal());
            if(!er.transactionSuccess()) {
                click.getPlayer().sendMessage("§cOcorreu um erro ao tentar vender.");
                return;
            }
            click.set("spawner", sp.removeDrop(sp.getDrops()));
            click.update();
        });
    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return SpawnerManager.getSpawner(sp.getLocation());
    }
}
