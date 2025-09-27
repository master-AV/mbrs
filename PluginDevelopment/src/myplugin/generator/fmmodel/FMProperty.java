package myplugin.generator.fmmodel;

public class FMProperty extends FMElement  {
	//Property type
	private String type;
	private String visibility;
	
	private Integer lower;
	private Integer upper;
	
	// Viki: dodatni atributi koji su korisni prilikom generisanja fajlova
	private String columnName;
	private String dbType;
	private Integer length;
	private Boolean required = false;
	private Integer precision;
	private Integer precisionScale;
	private boolean concurrencyCheck;

	//private boolean referenced = false;
	//private String relationshipAnnotation;
	//private FMProperty oppositeProperty;
	private String packagePath;

	private String lblName;
	//protected String mappedBy;
	
	/** @ToDo: Add length, precision, unique... whatever is needed for ejb class generation
	 * Also, provide these meta-attributes or tags in the modeling languange metaclass or 
	 * stereotype */

	
	public FMProperty(String name, String type, String visibility, int lower, int upper) {
		super(name);
		this.type = type;
		this.visibility = visibility;
		
		this.lower = lower;
		this.upper = upper;		
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}
/*
	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced = referenced;
	}

	public String getRelationshipAnnotation() {
		return relationshipAnnotation;
	}

	public void setRelationshipAnnotation(String annotation) {
		this.relationshipAnnotation = annotation;
	}
*/
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
	/*public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}*/

	public String getLblName() {
		return lblName;
	}

	public void setLblName(String lblName) {
		this.lblName = lblName;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
/*
	public FMProperty getOppositeProperty() {
		return oppositeProperty;
	}

	public void setOppositeProperty(FMProperty oppositeProperty) {
		this.oppositeProperty = oppositeProperty;
	}*/

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Integer getPrecisionScale() {
		return precisionScale;
	}

	public void setPrecisionScale(Integer precisionScale) {
		this.precisionScale = precisionScale;
	}

	public boolean isConcurrencyCheck() {
		return concurrencyCheck;
	}

	public void setConcurrencyCheck(boolean concurrencyCheck) {
		this.concurrencyCheck = concurrencyCheck;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}
	
	
}
