package me.approximations.spawners.model;


import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import lombok.experimental.Accessors;
import me.approximations.spawners.Main;
import me.approximations.spawners.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.*;

@Data
@Accessors(chain = true)
public class Spawner {
    private String dono;
    private String nome;
    private double quantia;
    private double drops;
    private String location;
    private HashMap<String, Amigo> amigos;
    private EntityType entityType;
    private String spawnerWrapperKey;

    public Spawner(String dono, double quantia, Location location, SpawnerWrapper sw) {
        this.dono = dono;
        this.nome = sw.getMobName();
        this.quantia = quantia;
        this.drops = 0D;
        this.location = LocationSerializer.getInstance().encode(location);
        this.amigos = new HashMap<>();
        this.entityType = sw.getEntityType();
        spawnerWrapperKey = sw.getKey();
    }

    public void addAmigo(Amigo amigo) {
        this.amigos.put(amigo.getNome(), amigo);
        Main.getInstance().getSpawnerManager().setSpawner(this);
    }

    public void removeAmigo(Amigo amigo) {
        this.amigos.remove(amigo.getNome());
        Main.getInstance().getSpawnerManager().setSpawner(this);
    }

    public Amigo getAmigoByName(String name) {
        return amigos.get(name);
    }

    public Spawner addQuantia(double quantia) {
        this.quantia += quantia;
        Main.getInstance().getSpawnerManager().setSpawner(this);
        return this;
    }
    public Spawner removeQuantia(double quantia) {
        this.quantia -= quantia;
        Main.getInstance().getSpawnerManager().setSpawner(this);
        return this;
    }

    public Spawner addDrop(double drops) {
        this.drops += drops;
        Main.getInstance().getSpawnerManager().setSpawner(this);
        return this;
    }
    public Spawner removeDrop(double drops) {
        this.drops -= drops;
        Main.getInstance().getSpawnerManager().setSpawner(this);
        return this;
    }

    public Location getLocation() {
        return LocationSerializer.getInstance().decode(this.location);
    }

    public void setAmigoCanBreak(Amigo amigo, boolean canBreak) {
        amigo.setCanBreak(canBreak);
        this.getAmigos().replace(amigo.getNome(), amigo);
        Main.getInstance().getSpawnerManager().setSpawner(this);
    }

    public void setAmigoCanPlace(Amigo amigo, Boolean canPlace) {
        amigo.setCanPlace(canPlace);
        this.getAmigos().replace(amigo.getNome(), amigo);
        Main.getInstance().getSpawnerManager().setSpawner(this);
    }

    public void setAmigoCanKill(Amigo amigo, Boolean canKill) {
        amigo.setCanKill(canKill);
        this.getAmigos().replace(amigo.getNome(), amigo);
        Main.getInstance().getSpawnerManager().setSpawner(this);
    }

    public SpawnerWrapper getSpawnerWrapper() {
        return Main.getInstance().getSpawnerManager().getSpawnerWrapper(this.spawnerWrapperKey);
    }
}
