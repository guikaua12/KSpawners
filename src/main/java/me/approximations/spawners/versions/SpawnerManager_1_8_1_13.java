package me.approximations.spawners.versions;

import com.google.common.collect.ImmutableMap;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import lombok.val;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.manager.model.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        CreatureSpawner cs = (CreatureSpawner) spawner.getLocation().getBlock().getState();
        cs.setSpawnedType(spawner.getEntityType());
        Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
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
        ConfigurationSection spawnerSection = SpawnersConfig.get(SpawnersConfig::getSpawners).getConfigurationSection(sw.getKey()).getConfigurationSection("Item");
        ItemStack is = Utils.getItemFromConfig(spawnerSection, ImmutableMap.of("{quantia}", NumberUtils.format(quantia, false)));
        // {quantia}
//        List<String> lore = new ArrayList<>();
//        for (String s : spawnerSection.getStringList("Lore")) {
//            String colored = ColorUtil.colored(s);
//            String q = colored.replace("{quantia}", NumberUtils.format(quantia, false));
//            lore.add(q);
//        }
//        ItemStack is;
//        if(spawnerSection.getBoolean("CustomHead")) {
//            is = new ItemBuilder(spawnerSection.getString("Head_url"))
//                    .setName(ColorUtil.colored(spawnerSection.getString("Name")))
//                    .setLore(lore)
//                    .wrap();
//        }else {
//            String[] i = spawnerSection.getString("Item").split(":");
//            is = new ItemBuilder(TypeUtil.getMaterialFromLegacy(i[0]), Integer.parseInt(i[1]))
//                    .setName(ColorUtil.colored(spawnerSection.getString("Name")))
//                    .setLore(lore)
//                    .wrap();
//        }
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
