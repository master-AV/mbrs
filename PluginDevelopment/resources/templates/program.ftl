using System;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;

namespace Web // TODO: add specialnamespace
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = CreateHostBuilder(args).Build();

			<#if program.migrate>
            // Example: Run migrations or other scoped startup tasks
            using (var scope = host.Services.CreateScope())
            {
                var services = scope.ServiceProvider;
                try
                {
                    var db = services.GetRequiredService<DataContext>();
                    db.Database.Migrate();
                }
                catch (Exception ex)
                {
                    var logger = services.GetRequiredService<ILogger<Program>>();
                    logger.LogError(ex, "An error occurred while migrating or seeding the database.");
                }
            }
			</#if>

            host.Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args)
        {
            Host.CreateDefaultBuilder(args)
                // Web host setup
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();

                    // Kestrel options can be parametrized
                    webBuilder.UseKestrel(options =>
                    {
                        options.Limits.MaxRequestBodySize = long.MaxValue;
                        options.Limits.KeepAliveTimeout = TimeSpan.FromMinutes(${program.kestrelKeepAliveInMinutes});
                    });
                })
                // Logging setup
                .ConfigureLogging((context, logging) =>
                {
                    logging.AddConsole();
                    logging.AddDebug();
                })
                // Configuration sources
                .ConfigureAppConfiguration((context, config) =>
                {
                    config.AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                          .AddJsonFile($"appsettings.{context.HostingEnvironment.EnvironmentName}.json", optional: true)
                          .AddEnvironmentVariables();
                });
		}
    }
}