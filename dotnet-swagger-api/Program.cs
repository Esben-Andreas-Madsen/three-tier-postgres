using Microsoft.OpenApi.Models;
using dotnet_swagger_api.Controllers;

var builder = WebApplication.CreateBuilder(args);

// Register services
builder.Services.AddSingleton<UserService>();

// Add Swagger services
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    c.SwaggerDoc("v1", new OpenApiInfo { Title = "Layer2 API", Version = "v1" });
});

builder.Services.AddControllers();

var app = builder.Build();

// Enable Swagger middleware
app.UseSwagger();
app.UseSwaggerUI(c =>
{
    c.SwaggerEndpoint("/swagger/v1/swagger.json", "Layer2 API v1");
});

// Enable Controllers
app.MapControllers();

app.Run();
