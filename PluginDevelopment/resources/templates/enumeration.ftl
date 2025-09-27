namespace Model
{
	public enum ${name} 
	{
	<#list values as value>
		${value?cap_first},
	</#list>
	}
}