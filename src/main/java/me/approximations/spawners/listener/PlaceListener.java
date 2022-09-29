package me.approximations.spawners.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.approximations.spawners.Main;
import me.approximations.spawners.api.event.SpawnerPlaceEvent;
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
import org.bukkit.event.EventPriority;
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
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
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
        double quantia = nbtItem.getDouble("quantia");
        SpawnerWrapper sw = plugin.getSpawnerManager().getSpawnerWrapper(nbtItem.getString("sw"));
        Spawner sp = new Spawner(player.getName(), quantia, e.getClickedBlock().getLocation().add(0, 1, 0), sw);
        SpawnerPlaceEvent spawnerPlaceEvent = new SpawnerPlaceEvent(player, sp);
        Bukkit.getPluginManager().callEvent(spawnerPlaceEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlaceTeste(SpawnerPlaceEvent e) {
        e.setCancelled(true);
    }
}
