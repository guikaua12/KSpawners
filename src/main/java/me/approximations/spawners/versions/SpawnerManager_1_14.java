package me.approximations.spawners.versions;

import com.google.common.collect.ImmutableMap;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.manager.model.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.serializer.SpawnerSerializer;
import me.approximations.spawners.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class SpawnerManager_1_14 implements SpawnerManager {
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
            cs.setMinSpawnDelay(100);
            cs.setMaxSpawnDelay(100);
            cs.setSpawnRange(2);

            NamespacedKey ns = new NamespacedKey(Main.getInstance(), "spawner");
            cs.getPersistentDataContainer().set(ns, PersistentDataType.STRING, SpawnerSerializer.getInstance().encode(spawner));
            cs.update(true);
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
        if(!(location.getBlock().getState() instanceof CreatureSpawner)) return false;
        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
        return cs.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), "spawner"), PersistentDataType.STRING);
    }

    public boolean hasSpawner(Block block) {
        if(!(block.getState() instanceof CreatureSpawner)) return false;
        CreatureSpawner cs = (CreatureSpawner) block.getState();
        return cs.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), "spawner"), PersistentDataType.STRING);
    }

    public Spawner getSpawner(Location location) {
        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
        return SpawnerSerializer.getInstance().decode(cs.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "spawner"), PersistentDataType.STRING));
    }

    public Spawner getSpawner(Block block) {
        CreatureSpawner cs = (CreatureSpawner) block.getState();
        return SpawnerSerializer.getInstance().decode(cs.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "spawner"), PersistentDataType.STRING));
    }

    public boolean isSpawnerItem(ItemStack is) {
        return new NBTItem(is).hasKey("k-spawner");
    }
}
