package com.dincertekin.simpleskyblock.storage;

import com.dincertekin.simpleskyblock.storage.queries.IslandDao;
import com.dincertekin.simpleskyblock.SimpleSkyblock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {

    private Connection connection;

    public SqlManager() { this.initialise(); };

    public void initialise() {
        if (connection == null)
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + SimpleSkyblock.getPlugin(SimpleSkyblock.class).getDataFolder().getAbsolutePath() + "/database.db");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void populate() {
        IslandDao.createTables();
    }

}
