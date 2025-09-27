using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Model
{
<#if class.tableName?? >
	<#if class.schema?? >
	[Table("${class.tableName}", Schema = "${class.schema}")]
	<#else>
	[Table("${class.tableName}")]
	</#if>
</#if>
	${class.visibility} class ${class.name} {  
	
		[Key]
		public int Id { get; set; }
<#list properties as property>

	<#if property.precision?? && property.precision gt 0>
		<#if property.precisionScale?? && property.precisionScale gt 0>
    	[Precision(${property.precision}, ${property.precisionScale})]
		<#else>
    	[Precision(${property.precision})]
    	</#if>
	</#if>
	<#if property.columnName?? >
		<#if property.dbType?? >
    	[Column("${property.columnName}", TypeName="${property.dbType}")]
		<#else>
    	[Column(${property.columnName})]
    	</#if>
	</#if>   
	<#if property.length?? && property.length gt 0>
    	[MaxLength(${property.length})]
	</#if>     
	<#if property.required?? && property.required == true>
    	[Required]
	</#if>    
	<#if property.concurrencyCheck?? && property.concurrencyCheck == true>
    	[ConcurrencyCheck]
	</#if>     
	<#if property.upper == 1 >   
	    public ${property.type} ${property.name} { get; set; }
	<#elseif property.upper == -1 > 
	    public ICollection<${property.type}> ${property.name} { get; set; }
	<#else>   
	    <#list 1..property.upper as i>
	    public ${property.type} ${property.name}${i} { get; set; }  
		</#list>  
	</#if>   
</#list>  
	
	<#if class.versioning?? && class.versioning == true >
	    [Timestamp] 
		public byte[] RowVersion { get; set; } = default!;
	</#if>
	}
}
