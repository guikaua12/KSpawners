package me.approximations.spawners.command;

import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.util.NBTEditor;
import me.approximations.spawners.view.spawnersShop.SpawnersView;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;

public class SpawnersCommand {
    @Command(
        name = "spawners",
        target = CommandTarget.PLAYER
    )
    public void Command(Context<Player> e) {
        Player player = e.getSender();
//        if(i == 1) {
//            ItemStack is = player.getItemInHand();
//            String encoded = ItemSerializer.encode(is);
//            Main.getInstance().getConfig().set("item", encoded);
//            Main.getInstance().saveConfig();
//        }
//        if(i == 2) {
//            String encoded = Main.getInstance().getConfig().getString("item");
//            ItemStack is = ItemSerializer.decode(encoded);
//            player.setItemInHand(is);
//        }
//        Main.getInstance().getViewFrame().open(SpawnersView.class, player);
        SpawnerManager.giveSpawner(player, 3D, "Lobo");

    }

//    public void teste() {
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
//    }
}
