package me.approximations.spawners.util;

import lombok.Data;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

@Data
public final class FancyText {

    private final BaseComponent[] baseComponent;
    private final static HashMap<UUID, Consumer<Player>> callbacks = new HashMap<>();

    public FancyText(String message) {
        this.baseComponent = TextComponent.fromLegacyText(message);
    }

    public FancyText hover(HoverEvent.Action action, String... messages) {
        String message = String.join("\n", messages);
        for (BaseComponent component : baseComponent) {
            component.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(message)));
        }
        return this;
    }

    public FancyText hover(HoverEvent.Action action, String message) {
        for (BaseComponent component : baseComponent) {
            component.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(message)));
        }

        return this;
    }

    public FancyText click(Consumer<Player> consumer){
        UUID uuid = UUID.randomUUID();
        callbacks.put(uuid, consumer);
        Arrays.stream(this.baseComponent).forEach(b -> b.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fancytext:callback "+uuid)));
        return this;
    }

    public FancyText click(ClickEvent.Action action, String message) {
        for (BaseComponent component : baseComponent) {
            component.setClickEvent(new ClickEvent(action, message));
        }

        return this;
    }

    public static void registerListener(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void command(PlayerCommandPreprocessEvent e){
                if(e.getMessage().startsWith("/fancytext:callback")){
                    String[] args = e.getMessage().split(" ");
                    if(args.length==2){
                        if(args[1].split("-").length==5){
                            UUID uuid = UUID.fromString(args[1]);
                            Consumer<Player> c = callbacks.remove(uuid);
                            if(c!=null){
                                c.accept(e.getPlayer());
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }, plugin);
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, callbacks::clear, 3600*20, 3600*20);
    }

    public BaseComponent[] build() {
        return this.baseComponent;
    }

}