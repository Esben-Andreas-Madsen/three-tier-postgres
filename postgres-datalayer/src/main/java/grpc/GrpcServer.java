package grpc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import database.postgres.DAOs.ArtifactDAO;
import database.postgres.DAOs.ArtifactDAOImpl;
import database.postgres.DAOs.ArtifactHistoryDAO;
import database.postgres.DAOs.ArtifactHistoryDAOImpl;
import database.postgres.services.ArtifactHistoryService;
import database.postgres.services.ArtifactHistoryServiceImpl;
import database.postgres.services.ArtifactServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class GrpcServer {

    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
    private final int port;
    private final Server server;

    // Connection pool
    private static final HikariDataSource dataSource;

    static {
        // Set up the connection pool
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/artifact_auction_db");
        config.setUsername("admin");
        config.setPassword("admin");
        config.setMaximumPoolSize(10);  // Max 10 connections
        config.setIdleTimeout(30000);   // Idle timeout for a connection (30 seconds)
        config.setConnectionTimeout(30000); // Timeout for acquiring a connection (30 seconds)
        config.setMinimumIdle(2);  // Minimum number of idle connections

        dataSource = new HikariDataSource(config);
    }

    public GrpcServer(int port) {
        this.port = port;

        ArtifactDAO artifactDAO = new ArtifactDAOImpl(dataSource);
        ArtifactHistoryDAO artifactHistoryDAO = new ArtifactHistoryDAOImpl(dataSource);

        ArtifactServiceImpl artifactServiceImpl = new ArtifactServiceImpl(artifactDAO);
        ArtifactHistoryService artifactHistoryService = new ArtifactHistoryServiceImpl(artifactHistoryDAO);

        this.server = ServerBuilder.forPort(port)
                .addService(new ArtifactGrpcService(artifactServiceImpl))
                .addService(new ArtifactHistoryGrpcService(artifactHistoryService))
                .build();
    }

    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server...");
            GrpcServer.this.stop();
            System.err.println("Server shut down.");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Get a connection from the pool
    }

    public static void shutdownPool() {
        if (dataSource != null) {
            dataSource.close(); // Shutdown the pool
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer(5051); // Adjust port as needed
        server.start();
        server.blockUntilShutdown();
    }
}
