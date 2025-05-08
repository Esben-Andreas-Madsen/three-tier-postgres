package grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.logging.Logger;

public class GrpcServer {

    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
    private final int port;
    private final Server server;

    public GrpcServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new UserServiceImpl()) // Register your service implementation
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

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer(5051); // Adjust port as needed
        server.start();
        server.blockUntilShutdown();
    }
}
