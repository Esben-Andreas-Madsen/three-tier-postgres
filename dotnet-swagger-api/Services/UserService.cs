using Grpc;
using Grpc.Net.Client;

public class UserService
{
    private readonly Grpc.UserService.UserServiceClient _grpcClient;

    public UserService()
    {
        // Connect to the Java gRPC server
        var channel = GrpcChannel.ForAddress("http://localhost:5051");
        _grpcClient = new Grpc.UserService.UserServiceClient(channel);
    }

    public async Task<UserResponse> GetUserByIdAsync(int id)
    {
        var request = new GetUserByIdRequest { Id = id };
        return await _grpcClient.GetUserByIdAsync(request);
    }

    public async Task<UserResponse> GetUserByUsernameAsync(string username)
    {
        var request = new GetUserByUsernameRequest { Username = username };
        return await _grpcClient.GetUserByUsernameAsync(request);
    }

    public async Task<UserResponse> CreateUserAsync(string username, string email, string passwordHash)
    {
        var request = new CreateUserRequest
        {
            Username = username,
            Email = email,
            PasswordHash = passwordHash,
        };

        return await _grpcClient.CreateUserAsync(request);
    }
}
