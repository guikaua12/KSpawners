package me.approximations.spawners.dao.adapter;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.User;
import me.approximations.spawners.serializer.UserSerializer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAdapter implements SQLResultAdapter<User> {
    @Override
    public User adaptResult(ResultSet rs) {
        User user = null;
        try {
            user = UserSerializer.getInstance().decode(rs.getString("user"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
