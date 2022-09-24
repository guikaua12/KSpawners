package me.approximations.spawners.listener;

import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.serializer.LocationSerializer;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(!block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) return;
        if(!SpawnerManager.hasSpawner(block)) return;
        e.setCancelled(true);
        Spawner sp = SpawnerManager.getSpawner(block);
        SpawnerWrapper sw = SpawnerManager.getSpawnersWrapper().get(sp.getSpawnerWrapperKey());
        Player player = e.getPlayer();
        if(!player.hasPermission("spawners.admin")) {
            if(!player.getName().equalsIgnoreCase(sp.getDono())) {
                Amigo amigo = sp.getAmigoByName(player.getName());
                if(amigo == null) {
                    player.sendMessage("§cVocê não tem permissão para remover geradores.");
                    return;
                }
                if(!amigo.isCanBreak()) {
                    e.getPlayer().sendMessage("§cVocê não tem permissão para remover geradores.");
                    return;
                }
            }
        }

        for (Entity entity : block.getWorld().getNearbyEntities(block.getLocation(), 5.0, 5.0, 5.0)) {
            if(!entity.getType().equals(EntityType.PLAYER)) {
                if(entity.hasMetadata("LOCATION") && LocationSerializer.getInstance().encode(block.getLocation()).equalsIgnoreCase(entity.getMetadata("LOCATION").get(0).asString())) {
                    entity.remove();
                    if(entity.getPassenger() != null) {
                        entity.getPassenger().remove();
                    }
                }
            }
        }

        double quantia = sp.getQuantia();
        Utils.giveItem(player, SpawnerManager.getSpawnerItem(sw, quantia));
        block.setType(Material.AIR);
    }
}
