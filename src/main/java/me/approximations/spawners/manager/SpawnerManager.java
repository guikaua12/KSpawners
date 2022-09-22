package me.approximations.spawners.manager;

import me.approximations.spawners.Main;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.serializer.SpawnerSerializer;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.NBTEditor;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnerManager {

    public static void setSpawner(Location location, Spawner spawner) {
        //spawnData
//        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
        NBTEditor.NBTCompound spawnData = NBTEditor.getEmptyNBTCompound();
        spawnData.set(SpawnerSerializer.getInstance().encode(spawner), "Spawner");
        NBTEditor.set(location.getBlock(), spawnData, "SpawnData");

        //spawnpotentials
        NBTEditor.NBTCompound properties = NBTEditor.getEmptyNBTCompound();
        properties.set(SpawnerSerializer.getInstance().encode(spawner), "Spawner");
        NBTEditor.NBTCompound potentials = NBTEditor.getNBTCompound(location.getBlock(), "SpawnPotentials", 0);
        potentials.set(properties, "Properties");
        NBTEditor.set(location.getBlock(), potentials, "SpawnPotentials");


//        NBTTagCompound properties = new NBTTagCompound();
//        properties.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//        NBTTagCompound potentials = compound.getList("SpawnPotentials", 10).get(0);
//        potentials.set("Properties", properties);
//        compound.set("SpawnPotentials", potentials);
    }

//    public static void setSpawner(Location location, Spawner spawner) {
//        boolean useArmorstand = SpawnerManager.getSpawnerSection(spawner.getNome()).getBoolean("armorstand");
//        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        NBTTagCompound spawnData = new NBTTagCompound();
//        if(!useArmorstand) {
//            //spawndata
//            spawnData.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//            compound.set("SpawnData", spawnData);
//            //spawnpotentials
//            NBTTagCompound properties = new NBTTagCompound();
//            properties.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//            NBTTagCompound potentials = compound.getList("SpawnPotentials", 10).get(0);
//            potentials.set("Properties", properties);
//            compound.set("SpawnPotentials", potentials);
//            //spawner em si
//            compound.setString("EntityId", SpawnerManager.getSpawnerSection(spawner.getNome()).getString("entity"));
//            compound.setShort("SpawnRange", (short) 2);
//            compound.setShort("MaxSpawnDelay", (short) 100);
//            compound.setShort("MinSpawnDelay", (short) 100);
//            compound.setShort("SpawnCount", (short) 1);
//            ts.a(compound);
//            ts.update();
//            cs.update();
//        }else {
//            String[] item = SpawnerManager.getSpawnerSection(spawner.getNome()).getString("item").split(":");
//            //spawndata
//            spawnData.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//            spawnData.setInt("Invisible", 1);
//            NBTTagList equipment = new NBTTagList();
//            NBTTagCompound hand = new NBTTagCompound();
//            NBTTagCompound b = new NBTTagCompound();
//            NBTTagCompound c = new NBTTagCompound();
//            NBTTagCompound d = new NBTTagCompound();
//            NBTTagCompound e = new NBTTagCompound();
//            hand.setString("id", item[0]);
//            hand.setInt("Damage", Integer.parseInt(item[1]));
//            hand.setInt("Count", 1);
//            equipment.add(hand);
//            equipment.add(b);
//            equipment.add(c);
//            equipment.add(d);
//            equipment.add(e);
//            spawnData.set("Equipment", equipment);
//            compound.set("SpawnData", spawnData);
//            //spawnpotentials
//            NBTTagCompound properties = new NBTTagCompound();
//            properties.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//            NBTTagCompound potentials = compound.getList("SpawnPotentials", 10).get(0);
//            potentials.set("Properties", properties);
//            compound.set("SpawnPotentials", potentials);
//            //spawner em si
//            compound.setString("EntityId", "ArmorStand");
//            compound.setShort("SpawnRange", (short) 2);
//            compound.setShort("MaxSpawnDelay", (short) 100);
//            compound.setShort("MinSpawnDelay", (short) 100);
//            compound.setShort("SpawnCount", (short) 1);
//            ts.a(compound);
//            ts.update();
//            cs.update();
//        }
//    }
//
    public static void updateSpawner(Location location, Spawner spawner) {
        //spawnData
        NBTEditor.NBTCompound spawnData = NBTEditor.getEmptyNBTCompound();
        spawnData.set(SpawnerSerializer.getInstance().encode(spawner), "Spawner");
        NBTEditor.set(location.getBlock(), spawnData, "SpawnData");

        //spawnpotentials
        NBTEditor.NBTCompound properties = NBTEditor.getEmptyNBTCompound();
        properties.set(SpawnerSerializer.getInstance().encode(spawner), "Spawner");
        NBTEditor.NBTCompound potentials = NBTEditor.getNBTCompound(location.getBlock(), "SpawnPotentials", 0);
        potentials.set(properties, "Properties");
        NBTEditor.set(location.getBlock(), potentials, "SpawnPotentials");
//        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        NBTTagCompound spawnData = compound.getCompound("SpawnData");
//        spawnData.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//        compound.set("SpawnData", spawnData);
//
//        NBTTagCompound properties = new NBTTagCompound();
//        properties.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//        NBTTagCompound potentials = compound.getList("SpawnPotentials", 10).get(0);
//        potentials.set("Properties", properties);
//        compound.set("SpawnPotentials", potentials);
//
//        ts.a(compound);
//        ts.update();
//        cs.update();
    }

    public static void updateSpawner(Block block, Spawner spawner) {
        //spawnData
        NBTEditor.NBTCompound spawnData = NBTEditor.getEmptyNBTCompound();
        spawnData.set(SpawnerSerializer.getInstance().encode(spawner), "Spawner");
        NBTEditor.set(block, spawnData, "SpawnData");

        //spawnpotentials
        NBTEditor.NBTCompound properties = NBTEditor.getEmptyNBTCompound();
        properties.set(SpawnerSerializer.getInstance().encode(spawner), "Spawner");
        NBTEditor.NBTCompound potentials = NBTEditor.getNBTCompound(block, "SpawnPotentials", 0);
        potentials.set(properties, "Properties");
        NBTEditor.set(block, potentials, "SpawnPotentials");
//        CreatureSpawner cs = (CreatureSpawner) block.getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        NBTTagCompound spawnData = compound.getCompound("SpawnData");
//        spawnData.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//        compound.set("SpawnData", spawnData);
//
//        NBTTagCompound properties = new NBTTagCompound();
//        properties.setString("Spawner", SpawnerSerializer.getInstance().encode(spawner));
//        NBTTagCompound potentials = compound.getList("SpawnPotentials", 10).get(0);
//        potentials.set("Properties", properties);
//        compound.set("SpawnPotentials", potentials);
//
//        ts.a(compound);
//        ts.update();
//        cs.update();
    }

    //TODO Adicionar customização do item na config
    public static void giveSpawner(Player player, double quantia, String nome) {
        //getSpawnerSection(nome).getString("head")
        ItemStack is = new ItemBuilder(TypeUtil.getMaterialFromLegacy("SKULL_ITEM"))
                .setName("§7Spawner de §e"+nome)
                .setLore("§7Quantidade: §2"+ NumberUtils.format(quantia, false))
                .wrap();

        is = NBTEditor.set(is, "k-spawner", "k-spawner");
        is = NBTEditor.set(is, quantia, "quantia");
        is = NBTEditor.set(is, nome, "nome");

        player.getInventory().addItem(is);
    }

    public static boolean hasSpawner(Location location) {
        return NBTEditor.getString(NBTEditor.getNBTCompound(location.getBlock(), "SpawnData"), "Spawner") != null;
//        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        if(compound.hasKey("SpawnData")) {
//            if(compound.getCompound("SpawnData").hasKey("Spawner")) {
//                return true;
//            }
//        }
//        return false;
    }

    public static boolean hasSpawner(Block block) {
        return NBTEditor.getString(NBTEditor.getNBTCompound(block, "SpawnData"), "Spawner") != null;
//        CreatureSpawner cs = (CreatureSpawner) block.getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        if(compound.hasKey("SpawnData")) {
//            if(compound.getCompound("SpawnData").hasKey("Spawner")) {
//                return true;
//            }
//        }
//        return false;
    }

    public static Spawner getSpawner(Location location) {
        return SpawnerSerializer.getInstance().decode(NBTEditor.getString(NBTEditor.getNBTCompound(location.getBlock(), "SpawnData"), "Spawner"));
//        CreatureSpawner cs = (CreatureSpawner) location.getBlock().getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        if(compound.hasKey("SpawnData")) {
//            NBTTagCompound spawnData = compound.getCompound("SpawnData");
//            if(spawnData.hasKey("Spawner")) {
//                return SpawnerSerializer.getInstance().decode(compound.getCompound("SpawnData").getString("Spawner"));
//            }
//        }
//        return null;
    }

    public static Spawner getSpawner(Block block) {
        return SpawnerSerializer.getInstance().decode(NBTEditor.getString(NBTEditor.getNBTCompound(block, "SpawnData"), "Spawner"));
//        CreatureSpawner cs = (CreatureSpawner) block.getState();
//        TileEntityMobSpawner ts = ((CraftCreatureSpawner) cs).getTileEntity();
//        NBTTagCompound compound = new NBTTagCompound();
//        ts.b(compound);
//        if(compound.hasKey("SpawnData")) {
//            NBTTagCompound spawnData = compound.getCompound("SpawnData");
//            if(spawnData.hasKey("Spawner")) {
//                return SpawnerSerializer.getInstance().decode(compound.getCompound("SpawnData").getString("Spawner"));
//            }
//        }
//        return null;
    }

    public static ConfigurationSection getSpawnerSection(String nome) {
        return Main.getInstance().getSpawnersConfig().getConfig().getConfigurationSection(nome);
    }


}
