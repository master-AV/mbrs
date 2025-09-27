package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMStartup;
import myplugin.generator.fmmodel.back.config.FMApplication;
import myplugin.generator.options.GeneratorOptions;

public class StartupGenerator extends BasicGenerator {
	
	public StartupGenerator(GeneratorOptions generatorOptions) {
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
			out = getWriter();
			if (out != null) {
				context.clear();
				FMStartup startup = FMModel.getInstance().getStartup();
				context.put("startup", startup);
				//out = getWriter("appsettings.generated", v)
				getTemplate().process(context, out);
				out.flush();
			}
		} catch (TemplateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
