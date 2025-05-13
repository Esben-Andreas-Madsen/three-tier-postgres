using Microsoft.AspNetCore.Mvc;
using Grpc;
using Grpc.Net.Client;
using System.Threading.Tasks;
using Grpc.Core;

namespace dotnet_swagger_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly UserService _userService;

        public UserController(UserService userService)
        {
            _userService = userService;
        }

        [HttpGet("{id}")]
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
        }

        [HttpPost]
        public async Task<IActionResult> CreateUser([FromBody] CreateUserRequest request)
        {
            try
            {
                var response = await _userService.CreateUserAsync(request.Username, request.Email, request.PasswordHash);
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
    }
}
