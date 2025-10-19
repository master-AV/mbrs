package myplugin.generator.fmmodel;

public class FMParameter extends FMType {
	
	private String direction;

	public FMParameter(String name, String type, String direction) {
		super(name, type);
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
