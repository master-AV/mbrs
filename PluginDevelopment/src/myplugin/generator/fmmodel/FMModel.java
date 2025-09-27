package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;

import myplugin.generator.fmmodel.back.config.FMApplication;
import myplugin.generator.fmmodel.back.config.FMProgram;

/** FMModel: Singleton class. This is intermediate data structure that keeps metadata
 * extracted from MagicDraw model. Data structure should be optimized for code generation
 * using freemarker
 */

public class FMModel {
	
	private List<FMClass> classes = new ArrayList<FMClass>();
	private List<FMRelationship> efRelationships = new ArrayList<FMRelationship>();
	private List<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();
	private FMApplication application = null;
	private FMProgram program = new FMProgram("default");
	private FMStartup startup = new FMStartup("default");
	
	//....
	/** @ToDo: Add lists of other elements, if needed */
	private FMModel() {
		
	}
	
	private static FMModel model;
	
	public static FMModel getInstance() {
		if (model == null) {
			model = new FMModel();			
		}
		return model;
	}
	
	public List<FMClass> getClasses() {
		return classes;
	}
	public void setClasses(List<FMClass> classes) {
		this.classes = classes;
	}
	public List<FMEnumeration> getEnumerations() {
		return enumerations;
	}
	public void setEnumerations(List<FMEnumeration> enumerations) {
		this.enumerations = enumerations;
	}

	public List<FMRelationship> getRelationships() {
		return efRelationships;
	}
	public void setRelationships(List<FMRelationship> classes) {
		this.efRelationships = classes;
	}
	public void addRelationship(FMRelationship addRelationship) {
		for (int i = 0; i < efRelationships.size(); i++) {
			FMRelationship rel = efRelationships.get(i);
			if (rel.getFirstAttributeName().equals(addRelationship.getSecondAttributeName())
					&& rel.getFirstAttributeType().equals(addRelationship.getSecondAttributeType())
					&& rel.getSecondAttributeName().equals(addRelationship.getFirstAttributeName())
					&& rel.getSecondAttributeType().equals(addRelationship.getFirstAttributeType()))
				return;
		}
		this.efRelationships.add(addRelationship);
	}
	
	public void Clear() {
		this.classes = new ArrayList<FMClass>();
		this.efRelationships = new ArrayList<FMRelationship>();
		this.enumerations = new ArrayList<FMEnumeration>();
		this.application = null;
		this.program = new FMProgram("cleared to default");
		this.startup = new FMStartup("cleared to default");
	}

	public FMApplication getApplication() {
		return application;
	}

	public void setApplication(FMApplication application) {
		this.application = application;
	}

	public FMProgram getProgram() {
		return program;
	}

	public void setProgram(FMProgram program) {
		this.program = program;
	}

	public FMStartup getStartup() {
		return startup;
	}

	public void setStartup(FMStartup startup) {
		this.startup = startup;
	}
	
}
