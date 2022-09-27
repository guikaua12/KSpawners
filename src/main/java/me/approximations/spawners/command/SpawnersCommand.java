package me.approximations.spawners.command;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import me.approximations.spawners.Main;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import me.approximations.spawners.versions.SpawnerManager_1_14;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SpawnersCommand {
    @Command(
        name = "spawners",
        target = CommandTarget.PLAYER
    )
    public void Command(Context<Player> e) {
        Player player = e.getSender();
        Utils.giveItem(player, Main.getInstance().getSpawnerManager().getSpawnerItem(Main.getInstance().getSpawnerManager().getSpawnerWrapper("Lobo"), 10D));
        Block block = player.getLocation().subtract(0, 1, 0).getBlock();
        if(!block.getType().equals(TypeUtil.getMaterialFromLegacy("MOB_SPAWNER"))) return;
        NBTCompound c = new NBTTileEntity(block.getState());
        player.sendMessage(c.toString());
//        Spawner sp = Main.getInstance().getSpawnerManager().getSpawner(block);
//        sp.setDrops()
    }
}
