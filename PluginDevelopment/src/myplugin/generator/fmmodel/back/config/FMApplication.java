package myplugin.generator.fmmodel.back.config;

import myplugin.generator.fmmodel.FMElement;

public class FMApplication extends FMElement {
	
	private String connectionString;
	private String LogLevelDefault;
	private String allowedHosts;


	public FMApplication(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}


	public String getConnectionString() {
		return connectionString;
	}


	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}


	public String getLogLevelDefault() {
		return LogLevelDefault;
	}


	public void setLogLevelDefault(String logLevelDefault) {
		LogLevelDefault = logLevelDefault;
	}


	public String getAllowedHosts() {
		return allowedHosts;
	}


	public void setAllowedHosts(String allowedHosts) {
		this.allowedHosts = allowedHosts;
	}

}
