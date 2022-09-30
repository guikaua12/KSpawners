package me.approximations.spawners.api.event;

import lombok.Data;
import lombok.Getter;
import me.approximations.spawners.model.Spawner;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Data
public class SpawnerPlaceEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player player;
    private Spawner spawner;
    private double quantia;

    public SpawnerPlaceEvent(Player player, Spawner spawner, double quantia) {
        this.player = player;
        this.spawner = spawner;
        this.quantia = quantia;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
