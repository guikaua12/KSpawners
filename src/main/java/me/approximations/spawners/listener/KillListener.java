package me.approximations.spawners.listener;

import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {
//    @EventHandler
//    public void armorStandKillEvent(EntityDamageByEntityEvent e) {
//        Entity entity = e.getEntity();
//        if(!e.getDamager().getType().equals(EntityType.PLAYER)) return;
//        if(!entity.hasMetadata("heat-spawner")) return;
//        Location location = LocationSerializer.getInstance().decode(entity.getMetadata("LOCATION").get(0).asString());
//        if(!SpawnerManager.hasSpawner(location)) return;
//        Spawner sp = SpawnerManager.getSpawner(location);
//        Player player = (Player) e.getDamager();
//
//        if(!player.hasPermission("spawners.admin")) {
//            if(!player.getName().equalsIgnoreCase(sp.getDono())) {
//                Amigo amigo = sp.getAmigoByName(player.getName());
//                if(amigo == null) {
//                    player.sendMessage("§cVocê não tem permissão para matar nesse gerador.");
//                    e.setCancelled(true);
//                    return;
//                }
//                if(!amigo.getCanKill()) {
//                    player.sendMessage("§cVocê não tem permissão para matar nesse gerador.");
//                    e.setCancelled(true);
//                    return;
//                }
//            }
//        }
//
//        double drops = entity.getMetadata("QUANTIA").get(0).asDouble();
//        if(entity.getType().equals(EntityType.ARMOR_STAND)) {
//            entity.getPassenger().remove();
//            entity.remove();
//        }
//        sp.addDrop(drops);
//    }

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
