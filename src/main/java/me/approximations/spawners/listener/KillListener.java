package me.approximations.spawners.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.dao.UserDao;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.User;
import me.approximations.spawners.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {
    private final Main plugin = Main.getInstance();
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMobDamage(EntityDamageByEntityEvent e) {
        if(!e.getEntity().hasMetadata("k-spawner")) return;
        Location location = LocationSerializer.getInstance().decode(e.getEntity().getMetadata("LOCATION").get(0).asString());
        Spawner spawner = plugin.getSpawnerManager().getSpawner(location);
        if(spawner == null) return;
        Player p = (Player) e.getDamager();
        if(!p.hasPermission("spawners.admin")) {
            if(!spawner.getDono().equalsIgnoreCase(p.getName())) {
                Amigo amigo = spawner.getAmigoByName(p.getName());
                if(amigo == null) {
                    p.sendMessage("§cVocê não tem permissão para matar mobs desse spawner!");
                    e.setCancelled(true);
                    return;
                }
                if(!amigo.isCanKill()) {
                    p.sendMessage("§cVocê não tem permissão para matar mobs desse spawner!");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
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
