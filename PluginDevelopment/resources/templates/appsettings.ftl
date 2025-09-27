{
  "ConnectionStrings": {
  <#if appsettings?? && appsettings.connectionString??>
    "DefaultConnection": "${appsettings.connectionString}"
  <#else>
  	"DefaultConnection": "dbConnection"
  </#if>
  },
  "Logging": {
    "LogLevel": {
 	<#if appsettings?? && appsettings.connectionString??>
      "Default": "${appsettings.logLevelDefault}",
	<#else>
	  "Default": "debug"
	 </#if>
    }
  },
<#if appsettings?? && appsettings.allowedHosts??>
  "AllowedHosts": "${appsettings.allowedHosts}"
<#else>
  "AllowedHosts": "*"
</#if>
}