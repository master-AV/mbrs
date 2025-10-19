package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMMethod;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.back.config.FMApplication;
import myplugin.generator.options.GeneratorOptions;

public class RepositoryGenerator extends ProtectedGenerator {

	public RepositoryGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {
		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			List<FMClass> classes = FMModel.getInstance().getClasses();
			//ArrayList<FMClass> repoClasses = new ArrayList<>();
			for (FMClass c : classes) {
				for (FMMethod m : c.getRelatedMethods()) {
					if (m.isRepository()) {
						context.clear();
						Map<String, String> regions = extractProtectedRegions(this.getFullPath(c.getName()));
						out = getWriter(c.getName());
						context.put("repoClass", c);
						context.put("regions", regions);
						getTemplate().process(context, out);
						out.flush();
						//repoClasses.add(c);
						break;
					}
				}
			}
		} catch (TemplateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
