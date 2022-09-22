package me.approximations.spawners.dao.adapter;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import me.approximations.spawners.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAdapter implements SQLResultAdapter<User> {
    @Override
    public User adaptResult(ResultSet rs) {
        String nick = null;
        double spawnersComprados = -1;
        try {
            nick = rs.getString("nick");
            spawnersComprados = rs.getDouble("quantia");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new User(nick, spawnersComprados);
    }
}
