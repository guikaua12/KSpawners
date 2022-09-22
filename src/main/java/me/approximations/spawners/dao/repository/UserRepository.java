package me.approximations.spawners.dao.repository;

import com.jaoow.sql.executor.SQLExecutor;
import lombok.RequiredArgsConstructor;
import me.approximations.spawners.Main;
import me.approximations.spawners.model.User;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class UserRepository{
    private Main plugin;
    private SQLExecutor sqlExecutor;
    private String TABLE;

    public UserRepository(Main plugin, SQLExecutor sqlExecutor) {
        this.plugin = plugin;
        this.sqlExecutor = sqlExecutor;
        this.TABLE = plugin.getMconfig().getDbTable();
    }



    public void createTable() {
        sqlExecutor.executeAsync("CREATE TABLE IF NOT EXISTS "+TABLE+"(nick VARCHAR(255), quantia DOUBLE);");
    }

    public void insertOrUpdate(User user) {
        if(contains(user.getNick())) {
            update(user);
        }else {
            insert(user);
        }
    }

    public void insert(User user) {
        sqlExecutor.executeAsync("INSERT INTO "+TABLE+" VALUES(?, ?);", c -> {
            try {
                c.setString(1, user.getNick());
                c.setDouble(2, user.getSpawnersComprados());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void update(User user) {
        sqlExecutor.executeAsync("UPDATE "+TABLE+" SET quantia = ? WHERE nick = ?;", c -> {
            try {
                c.setDouble(1, user.getSpawnersComprados());
                c.setString(2, user.getNick());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public User get(String nick) {
        try {
            return sqlExecutor.queryAsync("SELECT * FROM "+TABLE+" WHERE nick = ?;", c -> {
                try {
                    c.setString(1, nick);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }, User.class).get().get();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean contains(String nick) {
        return get(nick) != null;
    }

    public void delete(String nick) {
        sqlExecutor.executeAsync("DELETE FROM "+TABLE+" WHERE nick = ?;");
    }

    public Set<User> getAll() {
        try {
            return sqlExecutor.queryManyAsync("SELECT * FROM "+TABLE+";", User.class).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
