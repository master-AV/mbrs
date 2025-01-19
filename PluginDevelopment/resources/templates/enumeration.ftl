package enumerations;

namespace Model
{
	public enum ${class.name} 
	{
	<#list properties as property>
		<#if property == properties?last>
		${property?cap_first}
		<#else>
		${property?cap_first},
		</#if>
	</#list>
	}
}