
namespace Model
{
	${class.visibility} class ${class.name} {  
	
		public Integer id { get; set; }
	
<#list properties as property>
	<#if property.precision?? && property.precision gt 0>
    	[Precision(${property.precision})]
	</#if>     
	<#if property.length?? && property.length gt 0>
    	[MaxLength(${property.length})]
	</#if>     
	<#if property.required?? && property.required == true>
    	[Required]
	</#if>     
	<#if property.upper == 1 >   
	    public ${property.type} ${property.name} { get; set; }
	<#elseif property.upper == -1 > 
	    public Set<${property.type}> ${property.name} { get; set; }
	<#else>   
	    <#list 1..property.upper as i>
	    public ${property.type} ${property.name}${i} { get; set; }
	</#list>  
	</#if>     
</#list>  
	
	}
}
