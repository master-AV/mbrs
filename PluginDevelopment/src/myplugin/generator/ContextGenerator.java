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

public class ContextGenerator {// extends BasicGenerator {
/*
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
			
			Set<Object> classesSet = new HashSet<Object>();
			for (int i = 0; i < classes.size(); i++) {
				FMClass cl = classes.get(i);
				Writer out;
				Map<String, Object> innerContext = new HashMap<String, Object>();
				//try {
					out = getWriter(cl.getName(), cl.getTypePackage());
					if (out != null) {
						//context.clear();
						innerContext.put("class", cl);
						innerContext.put("properties", cl.getProperties());
						innerContext.put("importedPackages", cl.getImportedPackages());
						classesSet.add(innerContext);
						//getTemplate().process(context, out);
						out.flush();
					}
				//} catch (TemplateException e) {
				//	JOptionPane.showMessageDialog(null, e.getMessage());
				//} catch (IOException e) {
					//JOptionPane.showMessageDialog(null, e.getMessage());
				//}
			}
			
			context.put("classes", classesSet);
			Writer out;
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
	}*/
}