package me.approximations.spawners.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class Spawner {
    private String dono;
    private String nome;
    private double quantia;
    private double drops;
    private String location;
    private List<Amigo> amigos;
    private double dropValorUnitario;
    private EntityType entityType;
    private String spawnerWrapperKey;

    public Spawner(String dono, double quantia, Location location, SpawnerWrapper sw) {
        this.dono = dono;
        this.nome = sw.getMobName();
        this.quantia = quantia;
        this.drops = 0D;
        this.location = LocationSerializer.getInstance().encode(location);
        this.amigos = new ArrayList<>();
        this.entityType = sw.getEntityType();
        spawnerWrapperKey = sw.getKey();
        SpawnerManager.setSpawner(this);
    }

    public double getDropsValorTotal() {
        return this.drops * this.dropValorUnitario;
    }

    public void addAmigo(Amigo amigo) {
        this.amigos.add(amigo);
        SpawnerManager.setSpawner(this);
    }

    public void removeAmigo(Amigo amigo) {
        this.amigos.remove(amigo);
        SpawnerManager.setSpawner(this);
    }

    public Amigo getAmigoByName(String name) {
        Optional<Amigo> a = amigos.stream().filter(amg -> amg.getNome().equalsIgnoreCase(name)).findFirst();
        return a.orElse(null);
    }

    public Spawner addQuantia(double quantia) {
        this.quantia += quantia;
        SpawnerManager.setSpawner(this);
        return this;
    }
    public Spawner removeQuantia(double quantia) {
        this.quantia -= quantia;
        SpawnerManager.setSpawner(this);
        return this;
    }

    public Spawner addDrop(double drops) {
        this.drops += drops;
        SpawnerManager.setSpawner(this);
        return this;
    }
    public Spawner removeDrop(double drops) {
        this.drops -= drops;
        SpawnerManager.setSpawner(this);
        return this;
    }

    public void setAmigoCanBreak(Amigo amigo, boolean canBreak) {
        this.removeAmigo(amigo);
        amigo.setCanBreak(canBreak);
        this.addAmigo(amigo);
        SpawnerManager.setSpawner(this);
    }

    public void setAmigoCanPlace(Amigo amigo, Boolean canPlace) {
        this.removeAmigo(amigo);
        amigo.setCanPlace(canPlace);
        this.addAmigo(amigo);
        SpawnerManager.setSpawner(this);
    }

    public void setAmigoCanKill(Amigo amigo, Boolean canKill) {
        this.removeAmigo(amigo);
        amigo.setCanKill(canKill);
        this.addAmigo(amigo);
        SpawnerManager.setSpawner(this);
    }

    public Location getLocation() {
        return LocationSerializer.getInstance().decode(this.location);
    }
}
