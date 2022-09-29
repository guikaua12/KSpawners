package me.approximations.spawners.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.api.event.SpawnerBreakEvent;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.serializer.LocationSerializer;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    private final Main plugin = Main.getInstance();
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(!block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) return;
        if(!plugin.getSpawnerManager().hasSpawner(block)) return;
        e.setCancelled(true);
        Spawner sp = plugin.getSpawnerManager().getSpawner(block);
        SpawnerWrapper sw = sp.getSpawnerWrapper();
        Player player = e.getPlayer();

        SpawnerBreakEvent spawnerBreakEvent = new SpawnerBreakEvent(player, sp);
        Bukkit.getPluginManager().callEvent(spawnerBreakEvent);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpawnerBreak(SpawnerBreakEvent e) {
        if(e.isCancelled()) return;
        Spawner spawner = e.getSpawner();
        SpawnerWrapper sw = spawner.getSpawnerWrapper();
        Player player = e.getPlayer();

        if(!player.hasPermission("spawners.admin")) {
            if(!player.getName().equalsIgnoreCase(spawner.getDono())) {
                Amigo amigo = spawner.getAmigoByName(player.getName());
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

        for (Entity entity : spawner.getLocation().getWorld().getNearbyEntities(spawner.getLocation(), 5.0, 5.0, 5.0)) {
            if(!entity.getType().equals(EntityType.PLAYER)) {
                if(entity.hasMetadata("LOCATION") && LocationSerializer.getInstance().encode(spawner.getLocation()).equalsIgnoreCase(entity.getMetadata("LOCATION").get(0).asString())) {
                    entity.remove();
                }
            }
        }

        double quantia = spawner.getQuantia();
        Utils.giveItem(player, plugin.getSpawnerManager().getSpawnerItem(sw, quantia));
        spawner.getLocation().getBlock().setType(Material.AIR);
    }
}
