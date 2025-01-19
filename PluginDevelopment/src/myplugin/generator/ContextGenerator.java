package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;

// Viki: generator - koji gde odredimo context nad kojem radimo generisanje -- radi generisanje
public class ContextGenerator extends BasicGenerator {

	public ContextGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		try {
			List<FMClass> classes = FMModel.getInstance().getClasses();
			Map<String, Object> context = new HashMap<String, Object>();
			
			// Viki: popunjavanje contexta
			Set<Object> classesSet = new HashSet<Object>();
			for (int i = 0; i < classes.size(); i++) {
				FMClass cl = classes.get(i);
				Map<String, Object> innerContext = new HashMap<String, Object>();
				innerContext.put("class", cl);
				innerContext.put("properties", cl.getProperties());
				innerContext.put("importedPackages", cl.getImportedPackages());
				classesSet.add(innerContext);
			}
			
			context.put("classes", classesSet);
			Writer out;
			//JOptionPane.showMessageDialog(null, "Path " + classes.get(0).getTypePackage());
			//JOptionPane.showMessageDialog(null, "Path " + classes.get(0).getName());
			out = getWriter("Databaseontext", classes.get(0).getTypePackage());
			getTemplate().process(context, out);
			out.flush();
		} catch (TemplateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
}