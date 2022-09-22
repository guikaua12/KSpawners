package me.approximations.spawners.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.approximations.spawners.model.Amigo;

public class AmigoSerializer implements Serializer<Amigo>{
    @Getter
    private static final SpawnerSerializer instance = new SpawnerSerializer();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public String encode(Amigo data) {
        return GSON.toJson(data);
    }

    public Amigo decode(String data) {
        return GSON.fromJson(data, Amigo.class);
    }
}
