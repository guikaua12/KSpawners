package me.approximations.spawners.dao.repository;

import com.jaoow.sql.executor.SQLExecutor;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.DatabaseConfig;
import me.approximations.spawners.model.User;
import me.approximations.spawners.serializer.UserSerializer;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class UserRepository{
    private final Main plugin;
    private final SQLExecutor sqlExecutor;
    private final String TABLE;

    public UserRepository(Main plugin, SQLExecutor sqlExecutor) {
        this.plugin = plugin;
        this.sqlExecutor = sqlExecutor;
        this.TABLE = DatabaseConfig.get(DatabaseConfig::getTable);
    }



    public void createTable() {
        sqlExecutor.execute("CREATE TABLE IF NOT EXISTS "+TABLE+"(nick VARCHAR(255), user VARCHAR(255));");
    }

    public void insertOrUpdate(User user) {
        if(contains(user.getNick())) {
            update(user);
        }else {
            insert(user);
        }
    }

    public void insert(User user) {
        sqlExecutor.execute("INSERT INTO "+TABLE+" VALUES(?, ?);", c -> {
            try {
                c.setString(1, user.getNick());
                c.setString(2, UserSerializer.getInstance().encode(user));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void update(User user) {
        sqlExecutor.execute("UPDATE "+TABLE+" SET user = ? WHERE nick = ?;", c -> {
            try {
                c.setString(1, UserSerializer.getInstance().encode(user));
                c.setString(2, user.getNick());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public User get(String nick) {
        return sqlExecutor.query("SELECT * FROM "+TABLE+" WHERE nick = ?;", c -> {
            try {
                c.setString(1, nick);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, User.class).orElse(null);
    }

    public boolean contains(String nick) {
        return get(nick) != null;
    }

    public void delete(String nick) {
        sqlExecutor.execute("DELETE FROM "+TABLE+" WHERE nick = ?;", c -> {
            try {
                c.setString(1, nick);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Set<User> getAll() {
        return sqlExecutor.queryMany("SELECT * FROM "+TABLE+";", User.class);
    }
}
