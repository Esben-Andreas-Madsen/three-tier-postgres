package database.postgres.DAOs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shared.Role;
import shared.User;

import java.sql.*;

public class UserDAO implements IUserDAO {
    private static final Logger logger = LogManager.getLogger(RoleDAO.class);


    public void createUser(Connection connection, User user) {
        String sql = "INSERT INTO users (username, email, password_hash, role_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setInt(4, user.getRole().getId());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }


    public User getUserById(Connection connection, int id) {
        String sql = """
                    SELECT
                        u.id AS user_id,
                        u.username,
                        u.email,
                        u.password_hash,
                        u.created_at,
                        r.id AS role_id,
                        r.name AS role_name
                    FROM users u
                    JOIN roles r ON u.role_id = r.id
                    WHERE u.id = ?
                """;


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        role,
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch user", e);
        }

        return null;
    }

    public User getUserByUsername(Connection connection, String username) {
        String sql = "SELECT u.*, r.id AS role_id, r.name AS role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.id " +
                "WHERE u.username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        role,
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }

            return null; // Not found
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch user by username", e);
        }
    }


    public void updateUserEmail(Connection connection, int id, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user email", e);
        }
    }

    public void deleteUser(Connection connection, int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
