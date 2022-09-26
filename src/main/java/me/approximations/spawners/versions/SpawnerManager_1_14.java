package me.approximations.spawners.versions;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import me.approximations.spawners.Main;
import me.approximations.spawners.manager.model.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.serializer.SpawnerSerializer;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
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
        // TODO: 24/09/2022 mudar pra pdc
        ItemStack is = sw.getColocavelItem().clone();
        List<String> lore = new ArrayList<>();
        for (String s : is.getItemMeta().getLore()) {
            lore.add(s.replace("{quantia}", NumberUtils.format(quantia, false)).replace("&", "§"));
        }
        // {quantia}
        is = new ItemBuilder(is).setLore(lore).wrap();
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
//        if(!block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER")))
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
