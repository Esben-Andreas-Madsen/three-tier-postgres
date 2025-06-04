package database.postgres.DAOs;

import DTOs.Auction;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionDAOImpl implements AuctionDAO {

    private final HikariDataSource dataSource;

    public AuctionDAOImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Auction createAuction(Auction auction) {
        String sql = """
                    INSERT INTO Auctions (artifact_id, start_date, end_date, starting_bid, highest_bid, status)
                    VALUES (?, ?, ?, ?, ?, ?)
                    RETURNING id
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auction.getArtifactId());
            stmt.setTimestamp(2, Timestamp.valueOf(auction.getStartDate().toString()));
            stmt.setTimestamp(3, Timestamp.valueOf(auction.getEndDate().toString()));
            stmt.setInt(4, auction.getStartBid());
            stmt.setInt(5, auction.getHighestBid()); // Can be null
            stmt.setString(6, auction.getStatus());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int generatedId = rs.getInt("id");
                auction.setId(generatedId);

                return auction;
            } else {
                throw new SQLException("Auction creation failed, no ID returned.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create auction", e);
        }
    }

    @Override
    public Auction getAuctionById(int id) {
        String sql = """
                SELECT id, artifact_id, start_date, end_date, starting_bid, highest_bid, status 
                FROM Auctions 
                WHERE id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Auction auction = new Auction();
                auction.setId(rs.getInt("id"));
                auction.setArtifactId(rs.getInt("artifact_id"));
                auction.setStartDate(rs.getTimestamp("start_date"));
                auction.setEndDate(rs.getTimestamp("end_date"));
                auction.setStartBid(rs.getInt("starting_bid"));
                auction.setHighestBid(rs.getInt("highest_bid"));
                auction.setStatus(rs.getString("status"));
                return auction;
            } else {
                throw new SQLException("Auction not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch auction with ID " + id, e);
        }
    }

    @Override
    public boolean updateAuction(Auction auction) {
        String sql = """
                    UPDATE Auctions
                    SET artifact_id = ?,
                        start_date = ?,
                        end_date = ?,
                        starting_bid = ?,
                        highest_bid = ?,
                        status = ?
                    WHERE id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auction.getArtifactId());
            stmt.setTimestamp(2, Timestamp.valueOf(auction.getStartDate().toString()));
            stmt.setTimestamp(3, Timestamp.valueOf(auction.getEndDate().toString()));
            stmt.setInt(4, auction.getStartBid());

            if (auction.getHighestBid() > 0) {
                stmt.setInt(5, auction.getHighestBid());
            } else {
                stmt.setNull(5, java.sql.Types.NUMERIC);
            }

            stmt.setString(6, auction.getStatus());
            stmt.setInt(7, auction.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update auction with ID " + auction.getId(), e);
        }
    }

    @Override
    public boolean deleteAuction(int id) {
        String sql = "DELETE FROM auctions WHERE id = ?";

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affedctedRows = stmt.executeUpdate();
            return affedctedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artifact", e);
        }
    }

    @Override
    public List<Auction> getAllAuctions() {
        String sql = "SELECT * FROM auctions";
        List<Auction> auctions = new ArrayList<>();

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Auction found = new Auction();
                found.setId(rs.getInt("id"));
                found.setId(rs.getInt("artifact_id"));
                found.setStartDate(rs.getDate("start_date"));
                found.setEndDate(rs.getDate("end_date"));
                found.setStartBid(rs.getInt("starting_bid"));
                found.setHighestBid(rs.getInt("highest_bid"));
                found.setStatus(rs.getString("status"));
                auctions.add(found);
            }
            return auctions;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all auctions", e);
        }
    }
}
