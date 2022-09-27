package me.approximations.spawners.view.spawner;

import me.approximations.spawners.Main;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
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
        Spawner sp = getSpawner(context);
        SpawnerWrapper sw = Main.getInstance().getSpawnerManager().getSpawnerWrapper(sp.getSpawnerWrapperKey());
        context.slot(13).onRender(render -> {
            render.setItem(sp.getDrops() < 1 ? new ItemBuilder(TypeUtil.convertFromLegacy("WEB", 0)).setName("§cVazio").wrap() : new ItemBuilder(sw.getDropItem()).setName("§aDrop de "+sw.getMobName()).setLore("",
                    "§fQuantia: §7"+ NumberUtils.format(sp.getDrops(), false),
                    "§fValor ao vender: §7"+NumberUtils.format(sp.getDrops() * sw.getDropPrice(), false)).wrap());
        }).onClick(click -> {
            if(sp.getDrops() < 1) return;
            EconomyResponse er = Main.getInstance().getEcon().depositPlayer(click.getPlayer(), sp.getDrops() * sw.getDropPrice());
            if(!er.transactionSuccess()) {
                click.getPlayer().sendMessage("§cOcorreu um erro ao tentar vender.");
                return;
            }
            click.set("spawner", sp.removeDrop(sp.getDrops()));
            click.updateSlot();
//            click.update();
        });
    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return Main.getInstance().getSpawnerManager().getSpawner(sp.getLocation());
    }
}
