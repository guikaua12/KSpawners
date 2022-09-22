package me.approximations.spawners.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.NBTEditor;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if(!item.getType().equals(TypeUtil.getMaterialFromLegacy("SKULL_ITEM"))) return;
        if(!NBTEditor.contains(item, "k-spawner")) return;
        e.setCancelled(true);

        Player player = e.getPlayer();
        String nome = NBTEditor.getString(item, "nome");
        double quantia = NBTEditor.getDouble(item, "quantia");

        List<Block> blocks = Utils.getBlocksBetweenPoints(e.getBlock().getLocation().clone().add(5, 5, 5), e.getBlock().getLocation().clone().subtract(5, 5, 5));
        for (Block block : blocks) {
            if(block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) {
                if(SpawnerManager.hasSpawner(block)) {
                    Spawner sp = SpawnerManager.getSpawner(block);
                    if(sp.getNome().equalsIgnoreCase(nome)) {
                        if(!player.hasPermission("spawners.admin")) {
                            if(!player.getName().equalsIgnoreCase(sp.getDono())) {
                                Amigo amigo = sp.getAmigoByName(player.getName());
                                if(amigo == null) {
                                    player.sendMessage("§cVocê não tem permissão para adicionar geradores.");
                                    return;
                                }
                                if(!amigo.isCanPlace()) {
                                    player.sendMessage("§cVocê não tem permissão para adicionar geradores.");
                                    return;
                                }
                            }
                        }
                        sp.addQuantia(quantia);
                        Utils.removeItemInHand(player, 1);
                        player.sendMessage("§aForam adicionados "+ NumberUtils.format(quantia, false) +" spawners.");
                        return;
                    }else {
                        player.sendMessage("§cHá spawners num raio de 5 blocos.");
                        return;
                    }
                }
            }
        }
        Utils.removeItemInHand(player, 1);
        Bukkit.getServer().getScheduler().runTask(Main.getInstance(), () -> e.getBlock().setType(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER")));
        Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
            Block block1 = e.getBlock();
            Spawner sp = new Spawner(player.getName(), nome, quantia, 0.0, block1.getLocation(), new ArrayList<>());
        }, 1L);
    }
}
