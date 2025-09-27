package myplugin.generator.fmmodel;

public class FMRelationship {

	private String type; // one of: OneToOne, OneToMany, ManyToMany
	
	private String firstAttributeType;
	private String firstAttributeName;
	
	private String secondAttributeType;
	private String secondAttributeName;
	
	private String columnName;
	private String deleteBehavior;
	private String joinTableName;
	
	public FMRelationship(String type, String firstAttributeType, String firstAttributeName,
			String secondAttributeType, String secondAttributeName) {
		this.type = type;
		this.firstAttributeName = firstAttributeName;
		this.firstAttributeType = firstAttributeType;
		this.secondAttributeName = secondAttributeName;
		this.secondAttributeType = secondAttributeType;
	}
	public String getType() {
		return this.type;
	}
	public String getFirstAttributeName() {
		return this.firstAttributeName.substring(0, 1).toUpperCase() + firstAttributeName.substring(1);
	}
	public String getFirstAttributeType() {
		return this.firstAttributeType;
	}
	public String getSecondAttributeName() {
		return this.secondAttributeName.substring(0, 1).toUpperCase() + secondAttributeName.substring(1);
	}
	public String getSecondAttributeType() {
		return this.secondAttributeType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDeleteBehavior() {
		return deleteBehavior;
	}
	public void setDeleteBehavior(String deleteBehavior) {
		this.deleteBehavior = deleteBehavior;
	}
	public String getJoinTableName() {
		return joinTableName;
	}
	public void setJoinTableName(String joinTableName) {
		this.joinTableName = joinTableName;
	}
	
	
}
