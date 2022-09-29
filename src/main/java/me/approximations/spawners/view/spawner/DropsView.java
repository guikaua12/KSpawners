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
    private final Main plugin;
    public DropsView(Main plugin) {
        super(DropsInventory.get(DropsInventory::size), DropsInventory.get(DropsInventory::name));
        this.plugin = plugin;
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
        //get the spawner
        Spawner sp = getSpawner(context);
        SpawnerWrapper sw = plugin.getSpawnerManager().getSpawnerWrapper(sp.getSpawnerWrapperKey());
        ConfigurationSection spawnerSection = SpawnersConfig.get(SpawnersConfig::getSpawners).getConfigurationSection(sw.getKey());
        ConfigurationSection vazioItem = DropsInventory.get(DropsInventory::vazioItem);
        ConfigurationSection dropItem = DropsInventory.get(DropsInventory::dropItem);
        //for debug purposes
        context.getPlayer().sendMessage(""+sp.getDrops());

        String[] dropIt = spawnerSection.getString("Drop-item").split(":");

        context.slot(dropItem.getInt("Slot")).rendered(() -> {
            if(sp.getDrops() < 1) {
                return Utils.getItemFromConfig(vazioItem);
            }else {
                return Utils.getItemFromConfig(dropItem, ImmutableMap.of(
                    "{quantia}", NumberUtils.format(sp.getDrops(), false),
                    "{valor_vender}", NumberUtils.format(sp.getDrops() * sw.getDropPrice(), false)
                ), TypeUtil.getMaterialFromLegacy(dropIt[0]), Integer.parseInt(dropIt[1]));
            }
        }).onClick(click -> {
            //deposit the money to the player
            if(sp.getDrops() < 1) return;
            EconomyResponse er = plugin.getEcon().depositPlayer(click.getPlayer(), sp.getDrops() * sw.getDropPrice());
            if(!er.transactionSuccess()) {
                click.getPlayer().sendMessage("§cOcorreu um erro ao tentar vender.");
                return;
            }
            //remove the spawner drops and set it to the context
            //the spawner is updated within the removeDrop() method so its safe
            click.set("spawner", sp.removeDrop(sp.getDrops()));
            click.getPlayer().sendMessage("§aVocê vendeu os drops do spawner!");
            //update
            click.update();
        });
    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return plugin.getSpawnerManager().getSpawner(sp.getLocation());
    }
}
