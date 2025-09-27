package myplugin.generator.fmmodel.back.config;

import myplugin.generator.fmmodel.FMElement;

public class FMProgram extends FMElement {

	private int kestrelKeepAliveInMinutes = 5;
	private boolean migrate = false;
	
	public FMProgram(String name) {
		super(name);
	}

	public int getKestrelKeepAliveInMinutes() {
		return kestrelKeepAliveInMinutes;
	}

	public void setKestrelKeepAliveInMinutes(int kestrelKeepAliveInMinutes) {
		this.kestrelKeepAliveInMinutes = kestrelKeepAliveInMinutes;
	}

	public boolean isMigrate() {
		return migrate;
	}

	public void setMigrate(boolean migrate) {
		this.migrate = migrate;
	}
	
	
}
