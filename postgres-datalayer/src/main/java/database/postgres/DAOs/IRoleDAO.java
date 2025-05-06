package database.postgres.DAOs;

import shared.Role;

import java.sql.Connection;

public interface IRoleDAO {
    void createRole(Connection connection, Role role);
    Role getRoleById(Connection connection, int id);
    Role getRoleByName(Connection connection, String name);
    void updateRole(Connection connection, Role role);
    void deleteRole(Connection connection, int id);
}
