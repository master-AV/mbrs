package myplugin.generator.options;

public class Resources {

	// Viki: stereotypes iz metamodela -- koji možemo da dodelimo klasama u modelu
	public static final String ONE_TO_ONE = "OneToOne";
	public static final String ONE_TO_MANY = "OneToMany";
	public static final String MANY_TO_ONE = "ManyToOne";
	public static final String MANY_TO_MANY = "ManyToMany";
	public static final String UI_PROPERTY = "PersistentProperty";
	public static final String APPSETTING = "Appsetting";
	public static final String PROGRAM = "Program";
	public static final String STARTUP = "Startup";

	public static final String METHOD = "Method";
	
	// GeneratorTypes
	public static final String CONTEXT_GENERATOR = "ContextGenerator";
	public static final String ENUMERATION_GENERATOR = "EnumerationGenerator";
	public static final String MODEL_GENERATOR = "EJBGenerator";
	public static final String APPSETTINGS_GENERATOR = "AppsettingsGenerator";
	public static final String PROGRAM_GENERATOR = "ProgramGenerator";
	
	public static final String STARTUP_GENERATOR = "StartupGenerator";

	public static final String REPOSITORY_GENERATOR = "RepositoryGenerator";
	public static final String SERVICE_GENERATOR = "ServiceGenerator";
	public static final String CONTROLLER_GENERATOR = "ControllerGenerator";
	public static final String DTO_GENERATOR = "DtoGenerator";
}
