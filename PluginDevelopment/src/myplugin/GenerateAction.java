package myplugin;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import myplugin.analyzer.AnalyzeException;
import myplugin.analyzer.EnumerationAnalyzer;
import myplugin.analyzer.ModelAnalyzer;
import myplugin.generator.AppsettingsGenerator;
import myplugin.generator.ContextGenerator;
import myplugin.generator.EJBGenerator;
import myplugin.generator.EnumerationGenerator;
import myplugin.generator.ProgramGenerator;
import myplugin.generator.StartupGenerator;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.Resources;

/** Action that activate code generation */
@SuppressWarnings("serial")
class GenerateAction extends MDAction{
	
	
	public GenerateAction(String name) {			
		super("", name, null, null);		
	}

	public void actionPerformed(ActionEvent evt) {
		
		if (Application.getInstance().getProject() == null) return;
		Package root = Application.getInstance().getProject().getModel();
		
		if (root == null) return;
	
		ModelAnalyzer analyzer = new ModelAnalyzer(root, "ejb");	
		//ContextAnalyzer contextAnalyzer = new ContextAnalyzer(root, "ejb");
		EnumerationAnalyzer enumerationAnalyzer = new EnumerationAnalyzer(analyzer);

		
		try {
			analyzer.prepareModel();	
			GeneratorOptions go = ProjectOptions.getProjectOptions().getGeneratorOptions().get(Resources.MODEL_GENERATOR);			
			EJBGenerator generator = new EJBGenerator(go);
			generator.generate();
			

			// Viki: postavljanje analize + generisanja
			//analyzer.prepareModel();	
			GeneratorOptions contextGO = ProjectOptions.getProjectOptions().getGeneratorOptions().get(Resources.CONTEXT_GENERATOR);			
			ContextGenerator contextGenerator = new ContextGenerator(contextGO);
			contextGenerator.generate();
			

			enumerationAnalyzer.prepareModel();
			GeneratorOptions goEnumeration = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get(Resources.ENUMERATION_GENERATOR);
			EnumerationGenerator enumerationGenerator = new EnumerationGenerator(goEnumeration);
			enumerationGenerator.generate();

			GeneratorOptions appsettingsOptions = ProjectOptions.getProjectOptions().getGeneratorOptions().get(Resources.APPSETTINGS_GENERATOR);			
			AppsettingsGenerator appsettingsGenerator = new AppsettingsGenerator(appsettingsOptions);
			appsettingsGenerator.generate();
			
			GeneratorOptions programOptions = ProjectOptions.getProjectOptions().getGeneratorOptions().get(Resources.PROGRAM_GENERATOR);			
			ProgramGenerator programGenerator = new ProgramGenerator(programOptions);
			programGenerator.generate();

			GeneratorOptions startupOptions = ProjectOptions.getProjectOptions().getGeneratorOptions().get(Resources.STARTUP_GENERATOR);			
			StartupGenerator startupGenerator = new StartupGenerator(startupOptions);
			startupGenerator.generate();
			
			/**  @ToDo: Also call other generators */ 
			JOptionPane.showMessageDialog(null, "Code is successfully generated! Generated code is in folder: " + go.getOutputPath() +
					                         ", package: " + go.getFilePackage());
			exportToXml();
			FMModel.getInstance().Clear();
		} catch (AnalyzeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} 			
	}
	
	private void exportToXml() {
		if (JOptionPane.showConfirmDialog(null, "Do you want to save FM Model?") == 
			JOptionPane.OK_OPTION)
		{	
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getSelectedFile().getAbsolutePath();
			
				XStream xstream = new XStream(new DomDriver());
				BufferedWriter out;		
				try {
					out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(fileName), "UTF8"));					
					xstream.toXML(FMModel.getInstance().getClasses(), out);
					xstream.toXML(FMModel.getInstance().getEnumerations(), out);
					
				} catch (UnsupportedEncodingException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());				
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());				
				}		             
			}
		}	
	}	  

}