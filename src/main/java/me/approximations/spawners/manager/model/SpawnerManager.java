package me.approximations.spawners.manager.model;

import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public interface SpawnerManager {
    SpawnerWrapper insertSpawnerWrapper(SpawnerWrapper sw);
    SpawnerWrapper removeSpawnerWrapper(String swKey);
    SpawnerWrapper getSpawnerWrapper(String swKey);
    boolean containsSpawnerWrapper(String swKey);
    void setSpawner(Spawner spawner);
    ItemStack getSpawnerItem(SpawnerWrapper sw, double quantia);
    boolean hasSpawner(Location location);
    boolean hasSpawner(Block block);
    Spawner getSpawner(Location location);
    Spawner getSpawner(Block block);
    boolean isSpawnerItem(ItemStack is);
}
