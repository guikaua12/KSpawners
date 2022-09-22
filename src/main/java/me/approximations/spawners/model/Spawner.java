package me.approximations.spawners.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import me.approximations.spawners.Main;
import me.approximations.spawners.manager.SpawnerManager;
import me.approximations.spawners.serializer.LocationSerializer;
import me.approximations.spawners.util.NumberUtils;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Spawner {
    private String dono;
    private String nome;
    private double quantia;
    private double drops;
    private String location;
    private List<Amigo> amigos;
    private double dropValorUnitario;

    public Spawner(String dono, String nome, double quantia, double drops, Location location, List<Amigo> amigos) {
        this.dono = dono;
        this.nome = nome;
        this.quantia = quantia;
        this.drops = drops;
        this.location = LocationSerializer.getInstance().encode(location);
        this.amigos = amigos;
        SpawnerManager.setSpawner(location, this);
    }

    public double getDropsValorTotal() {
        return this.drops * this.dropValorUnitario;
    }

    public void addAmigo(Amigo amigo) {
        this.amigos.add(amigo);
        SpawnerManager.updateSpawner(this.getLocation(), this);
    }

    public void removeAmigo(Amigo amigo) {
        this.amigos.remove(amigo);
        SpawnerManager.updateSpawner(this.getLocation(), this);
    }

    public Amigo getAmigoByName(String name) {
        Optional<Amigo> a = amigos.stream().filter(amg -> amg.getNome().equalsIgnoreCase(name)).findFirst();
        return a.orElse(null);
    }

    public Spawner addQuantia(double quantia) {
        this.quantia += quantia;
        SpawnerManager.updateSpawner(this.getLocation(), this);
        return this;
    }
    public Spawner removeQuantia(double quantia) {
        this.quantia -= quantia;
        SpawnerManager.updateSpawner(this.getLocation(), this);
        return this;
    }

    public Spawner addDrop(double drops) {
        this.drops += drops;
        SpawnerManager.updateSpawner(this.getLocation(), this);
        return this;
    }
    public Spawner removeDrop(double drops) {
        this.drops -= drops;
        SpawnerManager.updateSpawner(this.getLocation(), this);
        return this;
    }

    public void setAmigoCanBreak(Amigo amigo, boolean canBreak) {
        this.removeAmigo(amigo);
        amigo.setCanBreak(canBreak);
        this.addAmigo(amigo);
        SpawnerManager.updateSpawner(this.getLocation(), this);
    }

    public void setAmigoCanPlace(Amigo amigo, Boolean canPlace) {
        this.removeAmigo(amigo);
        amigo.setCanPlace(canPlace);
        this.addAmigo(amigo);
        SpawnerManager.updateSpawner(this.getLocation(), this);
    }

    public void setAmigoCanKill(Amigo amigo, Boolean canKill) {
        this.removeAmigo(amigo);
        amigo.setCanKill(canKill);
        this.addAmigo(amigo);
        SpawnerManager.updateSpawner(this.getLocation(), this);
    }

    public Location getLocation() {
        return LocationSerializer.getInstance().decode(this.location);
    }
}
