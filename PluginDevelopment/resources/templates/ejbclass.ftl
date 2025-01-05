
namespace Model
{
	${class.visibility} class ${class.name} {  
	
		public Integer id { get; set; }
	
	<#list properties as property>
		<#if property.upper == 1 >   
	      public ${property.type} ${property.name} { get; set; }
	    <#elseif property.upper == -1 > 
	      public Set<${property.type}> ${property.name} = new HashSet<${property.type}>() { get; set; }
	    <#else>   
	    	<#list 1..property.upper as i>
	      public ${property.type} ${property.name}${i} { get; set; }
			</#list>  
	    </#if>     
	</#list>  
	
	}
}
