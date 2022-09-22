package me.approximations.spawners.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.util.List;

public class ListSerializer implements Serializer<List> {
    @Getter
    private static final ListSerializer instance = new ListSerializer();

    @Getter
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public String encode(List data) {
        return GSON.toJson(data);
    }

    public List decode(String data) {
        return GSON.fromJson(data, List.class);
    }
}
