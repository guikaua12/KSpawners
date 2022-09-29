package me.approximations.spawners.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.User;

public class UserSerializer implements Serializer<User>{
    @Getter
    private static final UserSerializer instance = new UserSerializer();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public String encode(User data) {
        return GSON.toJson(data);
    }

    public User decode(String data) {
        return GSON.fromJson(data, User.class);
    }
}
