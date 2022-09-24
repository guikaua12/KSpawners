package me.approximations.spawners.listener;

import com.google.common.collect.ImmutableMap;
import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.view.spawner.MainView;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MenuOpenListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        if(!block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) return;
        if(!SpawnerManager.hasSpawner(block)) return;
        Spawner sp = SpawnerManager.getSpawner(block);
        Player player = e.getPlayer();

        if(!player.hasPermission("spawners.admin")) {
            if(!player.getName().equalsIgnoreCase(sp.getDono())) {
                Amigo amigo = sp.getAmigoByName(player.getName());
                if(amigo == null) {
                    player.sendMessage("§cVocê não tem permissão para abrir esse gerador.");
                    return;
                }
            }
        }

        Main.getInstance().getViewFrame().open(MainView.class, e.getPlayer(), ImmutableMap.of("spawner", sp));
    }
}
