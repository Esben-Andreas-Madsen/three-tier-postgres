package database.postgres.DAOs;

import shared.User;

import java.sql.Connection;

public interface IUserDAO {
    void createUser(Connection connection, User user);
    User getUserById(Connection connection, int id);
    User getUserByUsername(Connection connection, String username);
    void updateUserEmail(Connection connection, int id, String newEmail);
    void deleteUser(Connection connection, int id);
}
