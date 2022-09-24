package me.approximations.spawners.view.spawner;

import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DropsView extends View {
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
        context.slot(13).onRender(render -> {
            Spawner sp = getSpawner(render);
            SpawnerWrapper sw = SpawnerManager.getSpawnersWrapper().get(sp.getSpawnerWrapperKey());
            render.setItem(sp.getDrops() < 1 ? new ItemBuilder(TypeUtil.convertFromLegacy("WEB", 0)).setName("§cVazio").wrap() : new ItemBuilder(sw.getDropItem()).setName("§aDrop de "+sw.getMobName()).setLore("",
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
