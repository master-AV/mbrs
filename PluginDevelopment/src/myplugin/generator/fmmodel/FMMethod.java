package myplugin.generator.fmmodel;

import java.util.ArrayList;

public class FMMethod extends FMElement {
	
	private boolean repository;
	private boolean service;
	private boolean controller;
	private String apiType;
	private String apiEnpoint;
	private String operationType;
	
	private String returnType;
	private ArrayList<FMParameter> parameters;
 
	public FMMethod(String name) {
		super(name);
	}

	public boolean isRepository() {
		return repository;
	}

	public void setRepository(boolean repository) {
		this.repository = repository;
	}

	public boolean isService() {
		return service;
	}

	public void setService(boolean service) {
		this.service = service;
	}

	public boolean isController() {
		return controller;
	}

	public void setController(boolean controller) {
		this.controller = controller;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public ArrayList<FMParameter> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<FMParameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(FMParameter parameter) {
		if (this.parameters == null)
			this.parameters = new ArrayList<FMParameter>();
		this.parameters.add(parameter);
	}

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getApiEnpoint() {
		return apiEnpoint;
	}

	public void setApiEnpoint(String apiEnpoint) {
		this.apiEnpoint = apiEnpoint;
	}
	
}
