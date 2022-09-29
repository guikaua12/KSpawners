package me.approximations.spawners.listener;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import me.approximations.spawners.Main;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.LocationSerializer;
import me.approximations.spawners.util.NumberUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class SpawnListener implements Listener {
    private final Main plugin = Main.getInstance();
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {
        Block block = e.getSpawner().getBlock();
        if(!plugin.getSpawnerManager().hasSpawner(block)) return;
        Spawner sp = plugin.getSpawnerManager().getSpawner(block);
        Entity entity = e.getEntity();
        List<Entity> entities = entity.getNearbyEntities(5.0, 5.0, 5.0);
        for (Entity entity1 : entities) {
            if(entity1.getType().equals(entity.getType())) {
                if(entity1.hasMetadata("k-spawner")) {
                    double mobsquantia = sp.getQuantia() + entity1.getMetadata("QUANTIA").get(0).asDouble();
                    entity1.setMetadata("QUANTIA", new FixedMetadataValue(plugin, mobsquantia));
                    entity1.setCustomName("§a"+sp.getNome()+" §7▶ §f"+ NumberUtils.format(mobsquantia, false));
                    e.getEntity().remove();
                    return;
                }
            }
        }
        entity.setMetadata("k-spawner", new FixedMetadataValue(plugin, (short) 1));
        entity.setMetadata("LOCATION", new FixedMetadataValue(plugin, LocationSerializer.getInstance().encode(block.getLocation())));
        entity.setMetadata("QUANTIA", new FixedMetadataValue(plugin, sp.getQuantia()));
        entity.setCustomName("§a"+sp.getNome()+" §7▶ §f"+ NumberUtils.format(sp.getQuantia(), false));
        NBTEntity nbtEntity = new NBTEntity(entity);
        nbtEntity.setByte("NoAI", (byte) 1);
    }
}
