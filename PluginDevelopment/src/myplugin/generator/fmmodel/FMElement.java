package myplugin.generator.fmmodel;

/** Element - abstract ancestor for all model elements */

public abstract class FMElement {
	
	private String name;
	
	public FMElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean hasName() {
		return name != null;
	}

	
}
