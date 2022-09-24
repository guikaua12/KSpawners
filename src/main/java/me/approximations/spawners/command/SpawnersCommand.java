package me.approximations.spawners.command;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.util.Utils;
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
        Utils.giveItem(player, SpawnerManager.getSpawnerItem(SpawnerManager.getSpawnersWrapper().get("Lobo"), 10D));
        Block block = player.getLocation().subtract(0, 1, 0).getBlock();
        NBTCompound c = NBTInjector.getNbtData(block.getState());
        player.sendMessage(c.toString());
    }
}
