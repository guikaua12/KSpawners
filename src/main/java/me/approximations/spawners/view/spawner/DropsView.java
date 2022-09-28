package me.approximations.spawners.view.spawner;

import com.google.common.collect.ImmutableMap;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.configuration.inventory.DropsInventory;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class DropsView extends View {
    public DropsView() {
        super(DropsInventory.get(DropsInventory::size), DropsInventory.get(DropsInventory::name));
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
        ConfigurationSection spawnerSection = SpawnersConfig.get(SpawnersConfig::getSpawners).getConfigurationSection(sw.getKey());
        ConfigurationSection vazioItem = DropsInventory.get(DropsInventory::vazioItem);
        ConfigurationSection dropItem = DropsInventory.get(DropsInventory::dropItem);
        context.getPlayer().sendMessage(""+sp.getDrops());

        String[] dropIt = spawnerSection.getString("Drop-item").split(":");

        if(sp.getDrops() < 1 ) {
            context.slot(vazioItem.getInt("Slot")).onRender(render -> {
                render.setItem(Utils.getItemFromConfig(vazioItem));
            });
        }else {
            context.slot(dropItem.getInt("Slot"))
                    .onRender(render -> {
                        render.setItem(Utils.getItemFromConfig(dropItem, ImmutableMap.of(
                                "{quantia}", NumberUtils.format(sp.getDrops(), false),
                                "{valor_vender}", NumberUtils.format(sp.getDrops() * sw.getDropPrice(), false)
                        ), TypeUtil.getMaterialFromLegacy(dropIt[0]), Integer.parseInt(dropIt[1])));
                    }).onClick(click -> {
                        if(sp.getDrops() < 1) return;
                        EconomyResponse er = Main.getInstance().getEcon().depositPlayer(click.getPlayer(), sp.getDrops() * sw.getDropPrice());
                        if(!er.transactionSuccess()) {
                            click.getPlayer().sendMessage("§cOcorreu um erro ao tentar vender.");
                            return;
                        }
                        click.set("spawner", sp.removeDrop(sp.getDrops()));
//                        click.updateSlot();
                        click.update();
                        click.getPlayer().sendMessage("§aVocê vendeu os drops do spawner!");
                    });
        }
    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return Main.getInstance().getSpawnerManager().getSpawner(sp.getLocation());
    }
}
