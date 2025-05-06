package database.postgres.DAOs;

import shared.Role;

import java.sql.*;

public class RoleDAO implements IRoleDAO {

    @Override
    public void createRole(Connection connection, Role role) {
        String sql = "INSERT INTO roles (name) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role.getName());
            stmt.executeUpdate();

            // Set generated ID back to role object
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                role.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create role", e);
        }
    }

    @Override
    public Role getRoleById(Connection connection, int id) {
        String sql = "SELECT id, name FROM roles WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Role(rs.getInt("id"), rs.getString("name"));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch role by ID", e);
        }
    }

    @Override
    public Role getRoleByName(Connection connection, String name) {
        String sql = "SELECT id, name FROM roles WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Role(rs.getInt("id"), rs.getString("name"));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch role by name", e);
        }
    }

    @Override
    public void updateRole(Connection connection, Role role) {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.getName());
            stmt.setInt(2, role.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update role", e);
        }
    }

    @Override
    public void deleteRole(Connection connection, int id) {
        String sql = "DELETE FROM roles WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete role", e);
        }
    }
}
