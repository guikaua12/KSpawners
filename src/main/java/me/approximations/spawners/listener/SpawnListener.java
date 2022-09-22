package me.approximations.spawners.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.LocationSerializer;
import me.approximations.spawners.util.NBTEditor;
import me.approximations.spawners.util.NumberUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class SpawnListener implements Listener {
    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {
        Block block = e.getSpawner().getBlock();
        if(!SpawnerManager.hasSpawner(block)) return;
        Spawner sp = SpawnerManager.getSpawner(block);
//        boolean useArmorstand = SpawnerManager.getSpawnerSection(sp.getNome()).getBoolean("armorstand");
        Entity entity = e.getEntity();
        List<Entity> entities = entity.getNearbyEntities(5.0, 5.0, 5.0);
//        if(entity.getType().equals(EntityType.ARMOR_STAND)) {
//            ConfigurationSection SPAWNERS_CONFIG = Main.getSpawners().getConfig().getConfigurationSection(sp.getNome());
//            String[] item = SPAWNERS_CONFIG.getString("item-spigot").split(":");
//            String item_str = item[0]+":"+item[1];
//            for (Entity entity1 : entities) {
//                if(entity1.getType().equals(EntityType.ARMOR_STAND)) {
//                    if(entity1.hasMetadata("heat-spawner")) {
//                        if(entity1.getPassenger() != null) {
//                            ItemStack i = ((Item)entity1.getPassenger()).getItemStack();
//                            String s = i.getType().toString()+":"+i.getDurability();
//                            if(s.equalsIgnoreCase(item_str)) {
//                                double mobsquantia = sp.getQuantia() + entity1.getMetadata("QUANTIA").get(0).asDouble();
//                                entity1.setMetadata("QUANTIA", new FixedMetadataValue(Main.getInstance(), mobsquantia));
//                                entity1.setCustomName("§a"+sp.getNome()+" §7▶ §f"+ NumberUtils.format(mobsquantia, false));
//                                e.setCancelled(true);
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//            ArmorStand as = (ArmorStand) entity;
////            Location loc = sp.getLocation().clone().subtract(0.5, 0, 0.5);
//            if(as.getLocation().getY() < sp.getLocation().getY() || as.getLocation().getY() > sp.getLocation().getY()) {
//                as.teleport(new Location(as.getWorld(), as.getLocation().getX(), sp.getLocation().clone().subtract(0, 1, 0).getY(), as.getLocation().getZ()));
//            }
////            Bukkit.broadcastMessage("Spawner x: "+sp.getLocation().getX());
////            Bukkit.broadcastMessage("ArmorStand x: "+as.getLocation().getX());
////            Bukkit.broadcastMessage("Spawner z: "+sp.getLocation().getZ());
////            Bukkit.broadcastMessage("ArmorStand z: "+as.getLocation().getZ());
////            if(as.getLocation().getX() >= loc.getX() - 0.5 && as.getLocation().getX() <= loc.getX() + 0.5) {
////                as.teleport(sp.getLocation().clone().add(2, 0, 0));
////            }
////            if(as.getLocation().getZ() >= loc.getZ() - 0.5 && as.getLocation().getZ() <= loc.getZ() + 0.5) {
////                as.teleport(sp.getLocation().clone().add(0, 0, 2));
////            }
//            as.setHelmet(null);
//            as.setItemInHand(null);
//            as.setCanPickupItems(false);
//            as.setGravity(false);
//            as.setVisible(false);
//            as.setMaximumAir(100);
//            as.setSmall(false);
//            as.setCustomName("§a"+sp.getNome()+" §7▶ §f"+ NumberUtils.format(sp.getQuantia(), false));
//            as.setCustomNameVisible(true);
//            as.setMetadata("heat-spawner", new FixedMetadataValue(Main.getInstance(), (short) 1));
//            as.setMetadata("LOCATION", new FixedMetadataValue(Main.getInstance(), LocationSerializer.getInstance().encode(block.getLocation())));
//            as.setMetadata("QUANTIA", new FixedMetadataValue(Main.getInstance(), sp.getQuantia()));
//
//            ItemStack is = new ItemStack(Material.valueOf(item[0].toUpperCase()), 1, (short) Short.parseShort(item[1]));
//            Item i = as.getWorld().dropItem(as.getLocation(), is);
//            i.setPickupDelay(Integer.MAX_VALUE);
//            i.setMetadata("heat-spawner", new FixedMetadataValue(Main.getInstance(), (short) 1));
//            as.setPassenger(i);
//        }else {
        for (Entity entity1 : entities) {
            if(entity1.getType().equals(entity.getType())) {
                if(entity1.hasMetadata("k-spawner")) {
                    double mobsquantia = sp.getQuantia() + entity1.getMetadata("QUANTIA").get(0).asDouble();
                    entity1.setMetadata("QUANTIA", new FixedMetadataValue(Main.getInstance(), mobsquantia));
                    entity1.setCustomName("§a"+sp.getNome()+" §7▶ §f"+ NumberUtils.format(mobsquantia, false));
                    e.setCancelled(true);
                    return;
                }
            }
        }
        entity.setMetadata("k-spawner", new FixedMetadataValue(Main.getInstance(), (short) 1));
        entity.setMetadata("LOCATION", new FixedMetadataValue(Main.getInstance(), LocationSerializer.getInstance().encode(block.getLocation())));
        entity.setMetadata("QUANTIA", new FixedMetadataValue(Main.getInstance(), sp.getQuantia()));
        entity.setCustomName("§a"+sp.getNome()+" §7▶ §f"+ NumberUtils.format(sp.getQuantia(), false));
        NBTEditor.set(entity, 1, "NoAI");
    }
}
