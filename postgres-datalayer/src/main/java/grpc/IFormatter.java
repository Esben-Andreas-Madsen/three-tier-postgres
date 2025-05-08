package grpc;

import shared.User;

public interface IFormatter {
    User requestToUser(CreateUserRequest request);
    UserDTO userToRequest(User user);

}
