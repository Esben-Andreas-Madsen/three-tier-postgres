using Microsoft.AspNetCore.Mvc;
using Grpc;
using Grpc.Net.Client;
using System.Threading.Tasks;
using Grpc.Core;

namespace dotnet_swagger_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArtifactController : ControllerBase
    {
        private readonly ArtifactService _artifactService;

        public ArtifactController(ArtifactService userService)
        {
            _artifactService = userService;
        }

        /*         [HttpGet("{id}")]
                public async Task<IActionResult> GetUserById(int id)
                {
                    try
                    {
                        var response = await _userService.GetUserByIdAsync(id);
                        return Ok(new
                        {
                            Id = response.User.Id,
                            Username = response.User.Username,
                            Email = response.User.Email
                        });
                    }
                    catch (RpcException ex)
                    {
                        return StatusCode((int)ex.StatusCode, ex.Status.Detail);
                    }
                }

                [HttpGet("username/{username}")]
                public async Task<IActionResult> GetUserByUsername(string username)
                {
                    try
                    {
                        var response = await _userService.GetUserByUsernameAsync(username);
                        return Ok(new
                        {
                            Id = response.User.Id,
                            Username = response.User.Username,
                            Email = response.User.Email
                        });
                    }
                    catch (RpcException ex)
                    {
                        return StatusCode((int)ex.StatusCode, ex.Status.Detail);
                    }
                } */

        [HttpPost]
        public async Task<IActionResult> CreateArtifact([FromBody] CreateArtifactRequest request)
        {
            try
            {
                // Call the service to create the artifact
                var createdArtifact = await _artifactService.CreateArtifactAsync(request.Artifactproto);

                // Return the created artifact with the generated ID in the response
                return Ok(createdArtifact);  // This will return the ArtifactProto with the generated ID
            }
            catch (RpcException ex)
            {
                return StatusCode((int)ex.StatusCode, ex.Status.Detail);
            }
        }

/*         [HttpGet("{id}")]
        public async Task<IActionResult> GetArtifactById(int id)
        {
            try
            {
                var artifact = await _artifactService.GetArtifactAsync(id);
                return Ok(new
                {
                    Id = response.User.Id,
                    Username = response.User.Username,
                    Email = response.User.Email
                });
            }
            catch (RpcException ex)
            {
                return StatusCode((int)ex.StatusCode, ex.Status.Detail);
            }
        } */
    }
}
