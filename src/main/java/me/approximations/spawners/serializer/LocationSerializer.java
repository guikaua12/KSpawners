package me.approximations.spawners.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer implements Serializer<Location> {
    @Getter
    private static LocationSerializer instance = new LocationSerializer();
    public String encode(Location data) {
        return data.getWorld().getName()+";"+data.getX()+";"+data.getY()+";"+data.getZ()+";"+data.getYaw()+";"+data.getPitch();
    }

    public Location decode(String data) {
        String[] l = data.split(";");
        return new Location(Bukkit.getWorld(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]), Float.parseFloat(l[4]), Float.parseFloat(l[5]));
    }
}
