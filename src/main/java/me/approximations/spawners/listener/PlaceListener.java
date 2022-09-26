package me.approximations.spawners.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.approximations.spawners.Main;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlaceListener implements Listener {
    private final Main plugin = Main.getInstance();
    @EventHandler
    public void onSpawnerPlace(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        ItemStack item = e.getPlayer().getItemInHand();
        if(item == null || item.getType().equals(Material.AIR)) return;
        NBTItem nbtItem = new NBTItem(item);
        if(!nbtItem.hasKey("k-spawner")) return;
        e.setCancelled(true);
        Player player = e.getPlayer();
        if (!e.getBlockFace().equals(BlockFace.UP)) {
            player.sendMessage("§cColoque o spawner na face de cima do bloco.");
            return;
        }
        SpawnerWrapper sw = plugin.getSpawnerManager().getSpawnerWrapper(nbtItem.getString("sw"));
        double quantia = nbtItem.getDouble("quantia");

        List<Block> blocks = Utils.getBlocksBetweenPoints(e.getClickedBlock().getLocation().clone().add(5, 5, 5), e.getClickedBlock().getLocation().clone().subtract(5, 5, 5));
        for (Block block : blocks) {
            if (block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) {
                if (plugin.getSpawnerManager().hasSpawner(block)) {
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
                        sp.addQuantia(quantia);
                        Utils.removeItemInHand(player, 1);
                        player.sendMessage("§aForam adicionados " + NumberUtils.format(quantia, false) + " spawners.");
                        return;
                    } else {
                        player.sendMessage("§cHá spawners num raio de 5 blocos.");
                        return;
                    }
                }
            }
        }
        Utils.removeItemInHand(player, 1);
        Block block1 = e.getClickedBlock().getLocation().add(0, 1, 0).getBlock();
        Bukkit.getServer().getScheduler().runTask(plugin, () -> block1.setType(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER")));
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            Spawner sp = new Spawner(player.getName(), quantia, block1.getLocation(), sw);
        }, 1L);
    }
}
