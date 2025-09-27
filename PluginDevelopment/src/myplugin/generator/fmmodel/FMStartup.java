package myplugin.generator.fmmodel;

public class FMStartup extends FMElement {
	
	private boolean distributedMemoryCache = false;
	private String appTitle;
	
	public FMStartup(String name) {
		super(name);
	}

	public boolean isDistributedMemoryCache() {
		return distributedMemoryCache;
	}

	public void setDistributedMemoryCache(boolean distributedMemoryCache) {
		this.distributedMemoryCache = distributedMemoryCache;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	
}
