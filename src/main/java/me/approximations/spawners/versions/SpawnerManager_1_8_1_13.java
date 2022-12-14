package me.approximations.spawners.versions;

import com.google.common.collect.ImmutableMap;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpawnerManager_1_8_1_13 implements SpawnerManager {
    @Getter
    private static final Map<String, SpawnerWrapper> spawnersWrapper = new LinkedHashMap<>();

    public SpawnerWrapper insertSpawnerWrapper(SpawnerWrapper sw) {
        return spawnersWrapper.put(sw.getKey(), sw);
    }

    public SpawnerWrapper removeSpawnerWrapper(String swKey) {
        return spawnersWrapper.remove(swKey);
    }

    public SpawnerWrapper getSpawnerWrapper(String swKey) {
        return spawnersWrapper.get(swKey);
    }

    public boolean containsSpawnerWrapper(String swKey) {
        return spawnersWrapper.containsKey(swKey);
    }

    public void setSpawner(Spawner spawner) {
        Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
            spawner.getLocation().getBlock().setType(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"));
            CreatureSpawner cs = (CreatureSpawner) spawner.getLocation().getBlock().getState();
            cs.setSpawnedType(spawner.getEntityType());
            NBTTileEntity nbte = new NBTTileEntity(spawner.getLocation().getBlock().getState());
            nbte.setInteger("MinSpawnDelay", 100);
            nbte.setInteger("MaxSpawnDelay", 100);
            nbte.setInteger("SpawnDelay", 100);
            nbte.setInteger("SpawnRange", 2);
            NBTCompound compound = NBTInjector.getNbtData(spawner.getLocation().getBlock().getState());
            compound.setObject("spawner", spawner);
        }, 1L);
    }

    public ItemStack getSpawnerItem(SpawnerWrapper sw, double quantia) {
        ConfigurationSection spawnerSection = SpawnersConfig.get(SpawnersConfig::getSpawners).getConfigurationSection(sw.getKey()).getConfigurationSection("Colocavel");
        ItemStack is = Utils.getItemFromConfig(spawnerSection, ImmutableMap.of("{quantia}", NumberUtils.format(quantia, false)));
        NBTItem nbi = new NBTItem(is);
        nbi.setString("k-spawner", "k-spawner");
        nbi.setDouble("quantia", quantia);
        nbi.setString("sw", sw.getKey());

        return nbi.getItem();
    }

    public boolean hasSpawner(Location location) {
        return NBTInjector.getNbtData(location.getBlock().getState()) != null;
    }

    public boolean hasSpawner(Block block) {
        return NBTInjector.getNbtData(block.getState()).getObject("spawner", Spawner.class) != null;
    }

    public Spawner getSpawner(Location location) {
        return NBTInjector.getNbtData(location.getBlock().getState()).getObject("spawner", Spawner.class);
    }

    public Spawner getSpawner(Block block) {
        return NBTInjector.getNbtData(block.getState()).getObject("spawner", Spawner.class);
    }

    public boolean isSpawnerItem(ItemStack is) {
        return new NBTItem(is).hasKey("k-spawner");
    }
}
