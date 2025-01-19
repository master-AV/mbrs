package myplugin.analyzer;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

public interface IAnalyzer {

	public void prepareModel() throws AnalyzeException;

	public Package getRoot();
	
	public String getFilePackage();
}
