package me.approximations.spawners.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.approximations.spawners.model.Spawner;

public class SpawnerSerializer implements Serializer<Spawner>{
    @Getter
    private static final SpawnerSerializer instance = new SpawnerSerializer();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public String encode(Spawner data) {
        return GSON.toJson(data);
    }

    public Spawner decode(String data) {
        return GSON.fromJson(data, Spawner.class);
    }
}
