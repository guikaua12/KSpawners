package me.approximations.spawners.api.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.api.event.SpawnerPlaceEvent;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class SpawnerPlaceListener implements Listener {
    private final Main plugin = Main.getInstance();
    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpawnerPlace(SpawnerPlaceEvent e) {
        if(e.isCancelled()) return;
        Spawner spawner = e.getSpawner();
        SpawnerWrapper sw = spawner.getSpawnerWrapper();
        Player player = e.getPlayer();
        List<Block> blocks = Utils.getBlocksBetweenPoints(spawner.getLocation().clone().add(5, 5, 5), spawner.getLocation().clone().subtract(5, 5, 5));
        for (Block block : blocks) {
            if (!block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) continue;
            if (!plugin.getSpawnerManager().hasSpawner(block)) continue;
            Spawner sp = plugin.getSpawnerManager().getSpawner(block);
            if (sp.getNome().equalsIgnoreCase(sw.getMobName())) {
                if (!player.hasPermission("spawners.admin")) {
                    if (!player.getName().equalsIgnoreCase(sp.getDono())) {
                        Amigo amigo = sp.getAmigoByName(player.getName());
                        if (amigo == null) {
                            player.sendMessage("§cVocê não tem permissão para adicionar geradores.");
                            return;
                        }
                        if (!amigo.isCanPlace()) {
                            player.sendMessage("§cVocê não tem permissão para adicionar geradores.");
                            return;
                        }
                    }
                }
                sp.addQuantia(spawner.getQuantia());
                Utils.removeItemInHand(player, 1);
                player.sendMessage("§aForam adicionados " + NumberUtils.format(spawner.getQuantia(), false) + " spawners.");
                return;
            } else {
                player.sendMessage("§cHá spawners num raio de 5 blocos.");
                return;
            }
        }
        Utils.removeItemInHand(player, 1);

        plugin.getSpawnerManager().setSpawner(spawner);
    }
}
