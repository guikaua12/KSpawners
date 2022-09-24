package me.approximations.spawners.manager;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SpawnerManager {
    @Getter
    private static final Map<String, SpawnerWrapper> spawnersWrapper = new LinkedHashMap<>();

    public static void setSpawner(Spawner spawner) {
        CreatureSpawner cs = (CreatureSpawner) spawner.getLocation().getBlock().getState();
        cs.setSpawnedType(spawner.getEntityType());
        cs.setDelay(100);
        NBTCompound compound = NBTInjector.getNbtData(spawner.getLocation().getBlock().getState());
        compound.setObject("spawner", spawner);
    }

    public static ItemStack getSpawnerItem(SpawnerWrapper sw, double quantia) {
        // {quantia}
        List<String> lore = new ArrayList<>();
        for (String s : sw.getColocavelItem().getItemMeta().getLore()) {
            lore.add(s.replace("{quantia}", NumberUtils.format(quantia, false)));
        }
        ItemStack is = new ItemBuilder(sw.getColocavelItem()).setLore(lore).wrap();
        NBTItem nbi = new NBTItem(is);
        nbi.setString("k-spawner", "k-spawner");
        nbi.setDouble("quantia", quantia);
        nbi.setString("sw", sw.getKey());

        return nbi.getItem();
    }

    public static boolean hasSpawner(Location location) {
        return NBTInjector.getNbtData(location.getBlock().getState()) != null;
    }

    public static boolean hasSpawner(Block block) {
        return NBTInjector.getNbtData(block.getState()) != null;
    }

    public static Spawner getSpawner(Location location) {
        return NBTInjector.getNbtData(location.getBlock().getState()).getObject("spawner", Spawner.class);
    }

    public static Spawner getSpawner(Block block) {
        return NBTInjector.getNbtData(block.getState()).getObject("spawner", Spawner.class);
    }


}
