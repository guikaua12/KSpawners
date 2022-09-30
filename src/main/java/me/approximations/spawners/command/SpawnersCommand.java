package me.approximations.spawners.command;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.MessagesConfig;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.ColorUtil;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import me.approximations.spawners.versions.SpawnerManager_1_14;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnersCommand {
    private final Main plugin = Main.getInstance();
    private final SpawnerManager spawnerManager = plugin.getSpawnerManager();
    @Command(
            name = "spawneradmin",
            target = CommandTarget.PLAYER,
            aliases = {"sadmin"},
            permission = "spawners.admin"
    )
    public void spawnerAdminCommand(Context<Player> e) {
        Player p = e.getSender();
        for (String s : MessagesConfig.get(MessagesConfig::spawnersAdmin)) {
            p.sendMessage(ColorUtil.colored(s));
        }
    }

    @Command(
            name = "spawneradmin.givespawner",
            target = CommandTarget.PLAYER,
            usage = "/spawneradmin givespawner <player> <spawner> <quantia>"
    )
    public void spawnerAdminCommand(Context<Player> e, Player target, String spawner, double quantia) {
        Player p = e.getSender();
        SpawnerWrapper sw = spawnerManager.getSpawnerWrapper(spawner);
        if(target == null) {
            p.sendMessage(MessagesConfig.get(MessagesConfig::jogadorInválido));
            return;
        }
        if(sw == null) {
            p.sendMessage(MessagesConfig.get(MessagesConfig::spawnerInvalido));
            return;
        }
        if(NumberUtils.isInvalid(quantia)) {
            p.sendMessage(MessagesConfig.get(MessagesConfig::quantiaInvalida));
            return;
        }
        ItemStack is = spawnerManager.getSpawnerItem(sw, quantia);
        Utils.giveItem(target, is);
        p.sendMessage(MessagesConfig.get(MessagesConfig::deuSpawner)
                .replace("{quantia}", NumberUtils.format(quantia, false))
                .replace("{mobName}", sw.getMobName())
                .replace("{target}", target.getName())
        );
    }
}
