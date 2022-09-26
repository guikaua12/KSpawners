package me.approximations.spawners.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {
    private final Main plugin = Main.getInstance();
    @EventHandler
    public void mobKillEvent(EntityDeathEvent e) {
        // TODO: 25/09/2022 melhorar o metadata
        Entity entity = e.getEntity();
        if(!entity.hasMetadata("k-spawner")) return;
        e.getDrops().clear();
        e.setDroppedExp(0);
        Location location = LocationSerializer.getInstance().decode(entity.getMetadata("LOCATION").get(0).asString());
        if(!plugin.getSpawnerManager().hasSpawner(location)) return;
        Spawner sp = plugin.getSpawnerManager().getSpawner(location);
        double drops = entity.getMetadata("QUANTIA").get(0).asDouble();
        sp.addDrop(drops);
    }
}
