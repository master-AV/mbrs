package myplugin;

import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.Resources;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

/** MagicDraw plugin that performes code generation */
public class MyPlugin extends com.nomagic.magicdraw.plugins.Plugin {
	
	String pluginDir = null; 
	
	public void init() {
		//JOptionPane.showMessageDialog( null, "My Plugin init");
		
		pluginDir = getDescriptor().getPluginDirectory().getPath();
		
		// Creating submenu in the MagicDraw main menu 	
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();		
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getSubmenuActions()));
		
		/** @Todo: load project options (@see myplugin.generator.options.ProjectOptions) from 
		 * ProjectOptions.xml and take ejb generator options */
		
		//for test purpose only:
		// Viki: generisani fajlovi bice na putanji: c:/temp
		// treci parametar je putanja do template-a, 
		// na depoy sve bi trebalo da prebaci iz resource na C, ali meni to nije radio konzistentno pa za sad sam ostavila sa apsolutnem putanjem
		String templateSource = "D:\\fakultet\\master-1\\MBRS\\proj\\mbrs\\PluginDevelopment\\resources\\templates";
		GeneratorOptions ejbOptions = new GeneratorOptions("c:/temp", "ejbclass", templateSource, "{0}.generated.cs", true, "model"); 		
		GeneratorOptions classOptions = new GeneratorOptions("c:/temp", "contextClass", templateSource, "{0}.cs", true, "model");
		GeneratorOptions enumerationOptions = new GeneratorOptions("c:/temp", "enumeration", templateSource, "{0}.generated.cs", true, "model");
		
		GeneratorOptions appsettingsOptions = new GeneratorOptions("c:/temp", "appsettings", templateSource, "appesettings.json", true, "model");
		GeneratorOptions programOptions = new GeneratorOptions("c:/temp", "program", templateSource, "Program.generated.cs", true, "config");
		GeneratorOptions startupOptions = new GeneratorOptions("c:/temp", "startup", templateSource, "Startup.generated.cs", true, "config");

		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.MODEL_GENERATOR, ejbOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.CONTEXT_GENERATOR, classOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.ENUMERATION_GENERATOR, enumerationOptions);
			
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.APPSETTINGS_GENERATOR, appsettingsOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.PROGRAM_GENERATOR, programOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.STARTUP_GENERATOR, startupOptions);
		
		GeneratorOptions repositoryOptions = new GeneratorOptions("c:/temp", "repositoryClass", templateSource, "{0}Repository.generated.cs", true, "repository");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.REPOSITORY_GENERATOR, repositoryOptions);

		GeneratorOptions serviceOptions = new GeneratorOptions("c:/temp", "serviceClass", templateSource, "{0}Service.generated.cs", true, "service");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.SERVICE_GENERATOR, serviceOptions);
		
		GeneratorOptions controllerOptions = new GeneratorOptions("c:/temp", "controllerClass", templateSource, "{0}Controller.generated.cs", true, "controller");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put(Resources.CONTROLLER_GENERATOR, controllerOptions);
		// Viki: kad prebaci templete na C, ovako nameste putanje do tih fajlova
		//ejbOptions.setTemplateDir(pluginDir + File.separator + ejbOptions.getTemplateDir()); //apsolutna putanja
		//classOptions.setTemplateDir(pluginDir + File.separator + classOptions.getTemplateDir());
		//JOptionPane.showMessageDialog( null, classOptions.getTemplateDir());
	}

	private NMAction[] getSubmenuActions()
	{
	   return new NMAction[]{
			new GenerateAction("Generate"), // Viki when Generate button is clicked it gets triggered
	   };
	}
	
	public boolean close() {
		return true;
	}
	
	public boolean isSupported() {				
		return true;
	}
}


