package grpc;

import com.google.protobuf.Timestamp;
import shared.User;

import java.time.ZoneId;

public class Formatter implements IFormatter{
    @Override
    public User requestToUser(CreateUserRequest request) {
        return new User(
                request.getUsername(),
                request.getEmail(),
                request.getPasswordHash(),
                request.getRoleId()
        );
    }

    @Override
    public UserDTO userToRequest(User user) {
        // Assuming RoleDTO is already defined for mapping Role to gRPC Role
        RoleDTO roleDTO = RoleDTO.newBuilder()
                .setId(user.getRole().getId())
                .setName(user.getRole().getName()) // Assuming Role has a getName method
                .build();

        // Convert LocalDateTime to Timestamp
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(user.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond())  // Convert LocalDateTime to Instant and then get epoch seconds
                .setNanos(user.getCreatedAt().getNano())  // Set the nanoseconds part
                .build();

        // Convert the User object to UserResponse

        return UserDTO.newBuilder()
                .setId(user.getId())  // Assuming User has a getId method
                .setUsername(user.getUsername())  // Assuming User has getUsername
                .setEmail(user.getEmail())  // Assuming User has getEmail
                .setPasswordHash(user.getPasswordHash())  // Assuming User has getPasswordHash
                .setRole(roleDTO)  // Set the RoleDTO object created earlier
                .setCreatedAt(timestamp)  // Assuming User has getCreatedAt() and it's a java.util.Date
                .build();
    }

}
