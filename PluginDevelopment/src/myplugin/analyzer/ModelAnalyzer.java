package myplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMMethod;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMParameter;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMRelationship;
import myplugin.generator.fmmodel.FMStartup;
import myplugin.generator.fmmodel.back.config.FMApplication;
import myplugin.generator.fmmodel.back.config.FMProgram;
import myplugin.generator.options.Resources;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Operation;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Parameter;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKind;
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
public class ModelAnalyzer implements IAnalyzer {	
	//root model package
	private Package root;
	
	//java root package for generated code
	private String filePackage;
	
	public ModelAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}

	public String getFilePackage() {
		return this.filePackage;
	}
	
	public void prepareModel() throws AnalyzeException {
		/*FMModel.getInstance().getClasses().clear();
		FMModel.getInstance().getRelationships().clear();
		FMModel.getInstance().setApplication(null);
		FMModel.getInstance().setO*/
		FMModel.getInstance().Clear();
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
					getClassData(cl, packageName);
				}		

				if (ownedElement instanceof Enumeration) {
					Enumeration en = (Enumeration)ownedElement;
					FMEnumeration fmEnumeration = getEnumerationData(en, packageName);
					FMModel.getInstance().getEnumerations().add(fmEnumeration);
				}
			}

			/** @ToDo:
			  * Process other package elements, as needed */ 
		}
	}
	
	private void getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null) 
			throw new AnalyzeException("Classes must have names!");
		
		if (StereotypesHelper.getAppliedStereotypeByString(cl, Resources.APPSETTING) != null) {
			getAppsettings(cl);
			return;
		}
		if (StereotypesHelper.getAppliedStereotypeByString(cl, Resources.PROGRAM) != null) {
			getProgram(cl);
			return;
		}

		if (StereotypesHelper.getAppliedStereotypeByString(cl, Resources.STARTUP) != null) {
			getStartup(cl);
			return;
		}
		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());
		Iterator<Property> it = ModelHelper.attributes(cl);
		while (it.hasNext()) {
			Property p = it.next();
			FMProperty prop = getPropertyData(p, cl);
			fmClass.addProperty(prop);	
		}	
		Iterator<Operation> op = ModelHelper.operations(cl);
		while (op.hasNext()) {
			Operation p = op.next();
			FMMethod method = getMethodData(p, cl);
			fmClass.addRelatedMethod(method);
		}
		FMModel.getInstance().getClasses().add(fmClass);
		
		/** @ToDo:
		 * Add import declarations etc. */	
	}

	private void getStartup(Class cl) {
		// TODO Auto-generated method stub
		FMStartup startup = new FMStartup(cl.getName());
		Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, Resources.STARTUP);
		startup.setAppTitle(getTagValue(cl, propStereotype, "appTitle"));
		startup.setDistributedMemoryCache(Boolean.valueOf(getTagValue(cl, propStereotype, "distributedMemoryCache")));
		FMModel.getInstance().setStartup(startup);
	}
	
	private void getProgram(Class cl) {
		// TODO Auto-generated method stub
		FMProgram program = new FMProgram(cl.getName());
		Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, Resources.PROGRAM);
		program.setKestrelKeepAliveInMinutes(Integer.valueOf(getTagValue(cl, propStereotype, "kestrelKeepAliveInMinutes")));
		program.setMigrate(Boolean.valueOf(getTagValue(cl, propStereotype, "migrate")));
		FMModel.getInstance().setProgram(program);
	}

	private void getAppsettings(Class cl) {// TODO: v continue <3
		//Iterator<Property> it = ModelHelper.attributes(cl);
		//while (it.hasNext()) {
			//Property p = it.next();
			FMApplication app = new FMApplication(cl.getName());
			Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, Resources.APPSETTING);
			app.setConnectionString(getTagValue(cl, propStereotype, "connectionString"));
			app.setLogLevelDefault(getTagValue(cl, propStereotype, "LogLevelDefault"));
			app.setAllowedHosts(getTagValue(cl, propStereotype, "allowedHosts"));
			FMModel.getInstance().setApplication(app);
		//}
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

			prop.setColumnName(getTagValue(p, propStereotype, "columnName"));
			prop.setDbType(getTagValue(p, propStereotype, "dbType"));
			prop.setLength(parseInt(getTagValue(p, propStereotype, "length")));
			prop.setRequired(Boolean.valueOf(getTagValue(p, propStereotype, "required")));
			prop.setPrecision(parseInt(getTagValue(p, propStereotype, "precision")));
			prop.setPrecisionScale(parseInt(getTagValue(p, propStereotype, "precisionScale")));
			prop.setConcurrencyCheck(Boolean.valueOf(getTagValue(p, propStereotype, "concurrencyCheck")));
			if (attType.has_associationOfEndType()) {
				Property oppositeProperty = p.getOpposite();
				FMProperty opp = new FMProperty(oppositeProperty.getName(), oppositeProperty.getType().getName(), oppositeProperty.getVisibility().toString(), 
						oppositeProperty.getLower(), oppositeProperty.getUpper());
				if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.ONE_TO_ONE) != null) {
					Stereotype associationStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.ONE_TO_ONE);
					FMRelationship rel = new FMRelationship(Resources.ONE_TO_ONE, prop.getType(), prop.getName(), opp.getType(), opp.getName());
					rel.setColumnName(getTagValue(p, associationStereotype, "columnName"));
					rel.setDeleteBehavior(getTagValue(p, associationStereotype, "deleteBehavior"));
					
					FMModel.getInstance().addRelationship(rel);
				} else if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.ONE_TO_MANY) != null) {
					Stereotype associationStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.ONE_TO_MANY);
					FMRelationship rel = new FMRelationship(Resources.ONE_TO_MANY, prop.getType(), prop.getName(), opp.getType(), opp.getName());
					rel.setColumnName(getTagValue(p, associationStereotype, "columnName"));
					rel.setDeleteBehavior(getTagValue(p, associationStereotype, "deleteBehavior"));
					FMModel.getInstance().addRelationship(rel);

				//} else if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.MANY_TO_ONE) != null) {
					//prop.setRelationshipAnnotation(Resources.MANY_TO_ONE);
					//FMRelationship rel = new FMRelationship(Resources.MANY_TO_ONE, prop.getType(), prop.getName(), opp.getType(), opp.getName());
					//FMModel.getInstance().addRelationship(rel);
				} else if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.MANY_TO_MANY) != null) {
					Stereotype associationStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.MANY_TO_MANY);
					FMRelationship rel = new FMRelationship(Resources.MANY_TO_MANY, prop.getType(), prop.getName(), opp.getType(), opp.getName());
					rel.setJoinTableName(getTagValue(p, associationStereotype, "joinTableName"));
					FMModel.getInstance().addRelationship(rel);
				}
			}
		}
		return prop;		
	}	

	private FMMethod getMethodData(Operation p, Class cl) {
		FMMethod method = new FMMethod(p.getName());
		//String methodName = p.getName();
		Stereotype methodStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.METHOD);
		method.setRepository(Boolean.valueOf(getTagValue(p, methodStereotype, "repo")));
		method.setService(Boolean.valueOf(getTagValue(p, methodStereotype, "service")));
		method.setController(Boolean.valueOf(getTagValue(p, methodStereotype, "controller")));
		method.setOperationType(getTagValue(p, methodStereotype, "operationType"));
		method.setApiType(getTagValue(p, methodStereotype, "apiType"));
		method.setApiEnpoint(getTagValue(p, methodStereotype, "apiEnpoint"));
		
		List<Parameter> params = p.getOwnedParameter();

	    for (Parameter param : params) {
	        String name = param.getName();
	        Type type = param.getType();
	        String direction = param.getDirection().toString(); // IN, OUT, INOUT, RETURN

	        if (direction.equals("in") || direction.equals("out")) {
	        	FMParameter parameter = new FMParameter(name, type.getName(), direction);
	        	method.addParameter(parameter);
	        } else if (direction.equals("return")) {
	        	method.setReturnType(type.getName());
	        }
	        
	        System.out.println("Parameter: " + name 
	            + " Type: " + (type != null ? type.getName() : "unspecified") 
	            + " Direction: " + direction);
	    }
	    return method;
	}
	
	private Integer parseInt(String value) {
		if (value == null)
			return null;
		else
			return Integer.parseInt(value);
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
	

	private FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size(); i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)  
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() +
				" must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}
}
