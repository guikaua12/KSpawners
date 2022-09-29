package me.approximations.spawners.dao.listener;

import me.approximations.spawners.Main;
import me.approximations.spawners.dao.UserDao;
import me.approximations.spawners.dao.repository.UserRepository;
import me.approximations.spawners.model.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent implements Listener {
    private final Main plugin = Main.getInstance();
    private final UserDao userDao = plugin.getUserDao();
    private final UserRepository userRepository = plugin.getUserRepository();
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            User user = userRepository.get(p.getName());
            if(user == null) {
                userDao.insert(new User(p.getName(), 0D));
                return;
            }
            userDao.insert(user);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            User user = userDao.getUsers().get(p.getName());
            userRepository.insertOrUpdate(user);
            userDao.remove(p.getName());
        });
    }
}
