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

public class ServiceGenerator extends BasicGenerator {

	public ServiceGenerator(GeneratorOptions generatorOptions) {
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
			for (FMClass c : classes) {
				for (FMMethod m : c.getRelatedMethods()) {
					if (m.isService()) {
						context.clear();
						out = getWriter(c.getName(), "service");
						context.put("serviceClass", c);
						getTemplate().process(context, out);
						out.flush();
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
