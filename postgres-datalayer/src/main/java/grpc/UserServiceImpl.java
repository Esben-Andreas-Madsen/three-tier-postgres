package grpc;

import database.postgres.DAOs.IRoleDAO;
import database.postgres.DAOs.IUserDAO;
import database.postgres.DAOs.RoleDAO;
import database.postgres.DAOs.UserDAO;
import database.postgres.DatabaseConnection;
import database.postgres.IDatabaseConnection;
import io.grpc.stub.StreamObserver;
import shared.User;


public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final IUserDAO userDAO;
    private final IDatabaseConnection connection;
    private final IFormatter formatter;

    public UserServiceImpl() {
        userDAO = new UserDAO();
        connection = new DatabaseConnection();
        formatter = new Formatter();
    }

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserDTO newUser = UserDTO.newBuilder()
                .setUsername(request.getUsername())
                .setEmail(request.getEmail())
                .setPasswordHash(request.getPasswordHash())
                .setRole(RoleDTO.newBuilder().setId(request.getRoleId()))
                .build();

        userDAO.createUser(connection.getConnection(), formatter.requestToUser(request));

        UserResponse response = UserResponse.newBuilder()
                .setUser(newUser)
                .build();

        // Send response back
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserById(GetUserByIdRequest request, StreamObserver<UserResponse> responseObserver) {
        // In real-world, fetch the user from the database by ID
        User user = userDAO.getUserById(connection.getConnection(), request.getId());

        UserResponse response = UserResponse.newBuilder()
                .setUser(formatter.userToRequest(user))
                .build();

        // Send response back
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserByUsername(GetUserByUsernameRequest request, StreamObserver<UserResponse> responseObserver) {
        // In real-world, fetch the user from the database by username
        User user = userDAO.getUserByUsername(connection.getConnection(), request.getUsername());


        UserResponse response = UserResponse.newBuilder()
                .setUser(formatter.userToRequest(user))
                .build();

        // Send response back
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
