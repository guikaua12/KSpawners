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
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class DropsView extends View {
    private final String SPAWNER_CONTEXT_KEY = "spawner";
    private final Main plugin;
    public DropsView(Main plugin) {
        super(DropsInventory.get(DropsInventory::size), DropsInventory.get(DropsInventory::name));
        this.plugin = plugin;
        setCancelOnClick(true);

        ConfigurationSection voltarItem = DropsInventory.get(DropsInventory::voltarItem);
        slot(voltarItem.getInt("Slot"), Utils.getItemFromConfig(voltarItem)).onClick(click -> {
            click.open(MainView.class, click.getData());
        });
    }

    @Override
    protected void onRender(ViewContext context) {
        Spawner sp = context.get(SPAWNER_CONTEXT_KEY);
        SpawnerWrapper sw = plugin.getSpawnerManager().getSpawnerWrapper(sp.getSpawnerWrapperKey());
        ConfigurationSection spawnerSection = SpawnersConfig.get(SpawnersConfig::getSpawners).getConfigurationSection(sw.getKey());
        ConfigurationSection vazioItem = DropsInventory.get(DropsInventory::vazioItem);
        ConfigurationSection dropItem = DropsInventory.get(DropsInventory::dropItem);

        String[] dropIt = spawnerSection.getString("Drop-item").split(":");

        context.slot(dropItem.getInt("Slot")).rendered(() -> {
            if(sp.getDrops() < 1) {
                return Utils.getItemFromConfig(vazioItem);
            }else {
                return Utils.getItemFromConfig(dropItem, ImmutableMap.of(
                        "{mobName}", sw.getMobName(),
                        "{quantia}", NumberUtils.format(sp.getDrops(), false),
                        "{valor_vender}", NumberUtils.format(sp.getDrops() * sw.getDropPrice(), false)
                ), TypeUtil.getMaterialFromLegacy(dropIt[0]), Integer.parseInt(dropIt[1]));
            }
        }).onClick(click -> {
            if(sp.getDrops() < 1) return;
            EconomyResponse er = plugin.getEcon().depositPlayer(click.getPlayer(), sp.getDrops() * sw.getDropPrice());
            if(!er.transactionSuccess()) {
                click.getPlayer().sendMessage("§cOcorreu um erro ao tentar vender.");
                return;
            }
            click.set(SPAWNER_CONTEXT_KEY, sp.removeDrop(sp.getDrops()));
            click.getPlayer().sendMessage("§aVocê vendeu os drops do spawner!");
            click.update();
        });
    }
}
