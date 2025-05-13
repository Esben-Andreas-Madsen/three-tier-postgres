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
                request.getPasswordHash()
        );
    }

    @Override
    public UserDTO userToRequest(User user) {
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
                .setCreatedAt(timestamp)  // Assuming User has getCreatedAt() and it's a java.util.Date
                .build();
    }

}
