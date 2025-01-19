package myplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.Resources;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/** Model Analyzer takes necessary metadata from the MagicDraw model and puts it in 
 * the intermediate data structure (@see myplugin.generator.fmmodel.FMModel) optimized
 * for code generation using freemarker. Model Analyzer now takes metadata only for ejb code 
 * generation

 * @ToDo: Enhance (or completely rewrite) myplugin.generator.fmmodel classes and  
 * Model Analyzer methods in order to support GUI generation. */ 

// Viki: zavisno od toga koji deo modela postavimo mozemo da dodajemo razlicite analyzere
public class ModelAnalyzer {	
	//root model package
	protected Package root;
	
	//java root package for generated code
	protected String filePackage;
	
	public ModelAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}
	
	public void prepareModel() throws AnalyzeException {
		FMModel.getInstance().getClasses().clear();
		FMModel.getInstance().getEnumerations().clear();
		processPackage(root, filePackage);
	}
	
	private void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		//Recursive procedure that extracts data from package elements and stores it in the 
		// intermediate data structure
		
		if (pack.getName() == null) throw  
			new AnalyzeException("Packages must have names!");
		
		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}
		
		if (pack.hasOwnedElement()) {
			
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Class) {
					Class cl = (Class)ownedElement;
					FMClass fmClass = getClassData(cl, packageName);
					FMModel.getInstance().getClasses().add(fmClass);
				}
				
				if (ownedElement instanceof Enumeration) {
					Enumeration en = (Enumeration)ownedElement;
					FMEnumeration fmEnumeration = getEnumerationData(en, packageName);
					FMModel.getInstance().getEnumerations().add(fmEnumeration);
				}								
			}
			
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Package) {					
					Package ownedPackage = (Package)ownedElement;
					if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "BusinessApp") != null)
						//only packages with stereotype BusinessApp are candidates for metadata extraction and code generation:
						processPackage(ownedPackage, packageName);
				}
			}
			
			/** @ToDo:
			  * Process other package elements, as needed */ 
		}
	}
	
	private FMClass getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null) 
			throw new AnalyzeException("Classes must have names!");
		
		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());
		Iterator<Property> it = ModelHelper.attributes(cl);
		while (it.hasNext()) {
			Property p = it.next();
			FMProperty prop = getPropertyData(p, cl);
			fmClass.addProperty(prop);	
		}	
		
		/** @ToDo:
		 * Add import declarations etc. */		
		return fmClass;
	}
	
	private FMProperty getPropertyData(Property p, Class cl) throws AnalyzeException {
		String attName = p.getName();
		if (attName == null) 
			throw new AnalyzeException("Properties of the class: " + cl.getName() +
					" must have names!");
		Type attType = p.getType();
		if (attType == null)
			throw new AnalyzeException("Property " + cl.getName() + "." +
			p.getName() + " must have type!");
		
		String typeName = attType.getName();
		if (typeName == null)
			throw new AnalyzeException("Type ot the property " + cl.getName() + "." +
			p.getName() + " must have name!");		
			
		int lower = p.getLower();
		int upper = p.getUpper();
		
		FMProperty prop = new FMProperty(attName, typeName, p.getVisibility().toString(), lower, upper);

		// Obrada property-ja
		// Viki: obradom modela dodelimo nasim klasma kao FMProperty dodatne obelezlje
		if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY) != null) {
			Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY);

			prop.setRequired(Boolean.valueOf(getTagValue(p, propStereotype, "required")));
			prop.setPrecision(parseInt(getTagValue(p, propStereotype, "precision")));
			prop.setLength(parseInt(getTagValue(p, propStereotype, "length")));
			if (attType.has_associationOfEndType()) {
				prop.setReferenced(true);
				Property oppositeProperty = p.getOpposite();
				FMProperty opp = new FMProperty(oppositeProperty.getName(), oppositeProperty.getType().getName(), oppositeProperty.getVisibility().toString(), 
						oppositeProperty.getLower(), oppositeProperty.getUpper());
				prop.setOppositeProperty(opp);
				//prop.setOppositeProperty();
				if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.ONE_TO_ONE) != null) {
					prop.setRelationshipAnnotation("OneToOne");
				} else if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.ONE_TO_MANY) != null) {
					prop.setRelationshipAnnotation("OneToMany");
				} else if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.MANY_TO_ONE) != null) {
					prop.setRelationshipAnnotation("ManyToOne");
				} else if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.MANY_TO_MANY) != null) {
					//add new class
					prop.setRelationshipAnnotation("ManyToMany");
				}
			}
		}
		return prop;		
	}	
	
	private Integer parseInt(String value) {
		if (value == null)
			return null;
		else
			return Integer.parseInt(value);
	}

	private FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size() - 1; i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)  
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() +
				" must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}	
	

	public String getTagValue(Element el, Stereotype s, String tagName) {
		@SuppressWarnings("rawtypes")
		List value = StereotypesHelper.getStereotypePropertyValueAsString(el, s, tagName);
		if (value == null)
			return null;
		if (value.size() == 0)
			return null;
		return (String) value.get(0);
	}
}
