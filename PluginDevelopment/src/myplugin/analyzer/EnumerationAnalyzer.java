package myplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;


import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;


public class EnumerationAnalyzer {
	
	private IAnalyzer basicAnalyzer;
	
	public EnumerationAnalyzer(IAnalyzer analyzer) {
		this.basicAnalyzer = analyzer;
	}

	public void prepareModel() throws AnalyzeException {
		FMModel.getInstance().getEnumerations().clear();
		processPackage(basicAnalyzer.getRoot(), basicAnalyzer.getFilePackage());
	}

	public void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		if (pack.getName() == null)
			throw new AnalyzeException("Packages must have names!");

		String packageName = packageOwner;
		if (pack != basicAnalyzer.getRoot()) {
			packageName += "." + pack.getName();
		}

		if (pack.hasOwnedElement()) {

			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();

				if (ownedElement instanceof Enumeration) {
					Enumeration en = (Enumeration) ownedElement;
					FMEnumeration fmEnumeration = getEnumerationData(en, packageName);
					FMModel.getInstance().getEnumerations().add(fmEnumeration);
				}
			}

			/*for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Package) {
					Package ownedPackage = (Package) ownedElement;
					if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "BusinessApp") != null)
						processPackage(ownedPackage, packageName);
				}

			}*/

		}
	}

	public FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size(); i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() + " must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}
}

