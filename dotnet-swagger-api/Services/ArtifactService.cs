using Grpc;
using Grpc.Net.Client;

public class ArtifactService
{
    private readonly Grpc.GatewayService.GatewayServiceClient _grpcClient;

    public ArtifactService()
    {
        // Connect to the Java gRPC server
        var channel = GrpcChannel.ForAddress("http://localhost:5051");
        _grpcClient = new Grpc.GatewayService.GatewayServiceClient(channel);
    }
    /* 
        public async Task<ArtifactResponse> GetUserByIdAsync(int id)
        {
            var request = new GetUserByIdRequest { Id = id };
            return await _grpcClient.GetUserByIdAsync(request);
        }

        public async Task<UserResponse> GetUserByUsernameAsync(string username)
        {
            var request = new GetUserByUsernameRequest { Username = username };
            return await _grpcClient.GetUserByUsernameAsync(request);
        } */

    public async Task<ArtifactProto> CreateArtifactAsync(ArtifactProto proto)
    {
        // Create the request with the ArtifactProto (without the ID)
        var request = new CreateArtifactRequest
        {
            Artifactproto = proto
        };

        // Send the request to the gRPC server
        var response = await _grpcClient.CreateArtifactAsync(request);

        // Return the ArtifactProto from the response, which will have the generated ID
        return response.Artifactproto;  // Assuming response.Artifactproto contains the created artifact with ID
    }

}
