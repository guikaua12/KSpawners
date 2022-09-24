package me.approximations.spawners.listener;

import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {

    @EventHandler
    public void killEvent(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if(!entity.hasMetadata("k-spawner")) return;
        e.getDrops().clear();
        e.setDroppedExp(0);
        Location location = LocationSerializer.getInstance().decode(entity.getMetadata("LOCATION").get(0).asString());
        if(!SpawnerManager.hasSpawner(location)) return;
        Spawner sp = SpawnerManager.getSpawner(location);
        double drops = entity.getMetadata("QUANTIA").get(0).asDouble();
        sp.addDrop(drops);
    }
}
