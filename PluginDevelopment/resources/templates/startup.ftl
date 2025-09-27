using System.Text;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;

namespace MyApp
{
    public class Startup
    {
        public Startup(IConfiguration configuration, IWebHostEnvironment env)
        {
            Configuration = configuration;
            Env = env;
        }

        public IConfiguration Configuration { get; }
        public IWebHostEnvironment Env { get; }

        // Register services
        public void ConfigureServices(IServiceCollection services)
        {
            // MVC / Web API
            services.AddControllers();

            // Repositories / Services (parametrizable DI setup)
            // services.AddScoped<IUserRepository, UserRepository>();

            // HttpContext access
            services.AddHttpContextAccessor();

            <#if startup.distributedMemoryCache>
            // Caching
            services.AddDistributedMemoryCache();
            </#if>

            // Swagger (optional)
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo
                {
                    Title = <#if startup.appTitle??> "${startup.appTitle}" <#else> "MyApp" </#if>
                    Version = "v1"
                });
            });

            // CORS (optional)
            var allowedOrigins = Configuration.GetSection("Cors:AllowedOrigins").Get<string[]>() ?? new string[] { "*" };
            services.AddCors(options =>
            {
                options.AddDefaultPolicy(policy =>
                    policy.WithOrigins(allowedOrigins)
                          .AllowAnyHeader()
                          .AllowAnyMethod()
                          .AllowCredentials());
            });
        }

        // Configure middleware
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment() || env.IsEnvironment("Localhost"))
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint(
                	"/swagger/v1/swagger.json", 
                <#if startup.appTitle??>
                	"${startup.appTitle}"
                <#else>
                	"MyApp"
                </#if>
                ));
            }

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseCors();

            app.UseAuthentication();
            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
