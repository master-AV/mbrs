namespace Service 
{
	public I${serviceClass.name}Service
	{
	<#list serviceClass.relatedMethods as method>
		Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>);
	</#list>  
	}
	
	public ${serviceClass.name}Service : I${serviceClass.name}Service
	{
	    private readonly I${serviceClass.name}Repository _repository;
	
	    public ${serviceClass.name}Service(I${serviceClass.name}Repository repository)
	    {
	        _repository = repository;
	    }
	    
<#list serviceClass.relatedMethods as method>
	<#if method.operationType == "other">
		partial Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>);
	<#elseif method.returnType?? && method.parameters??>
		public async Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>) 
		{
		<#if method.operationType == "create">
        	return await _repository.${method.name}(${method.parameters[0].name});
		<#elseif method.operationType == "read">
        	return await _repository.${method.name}(${method.parameters[0].name});
		<#elseif method.operationType == "readAll">
        	return await _repository.${method.name}();
		<#elseif method.operationType == "update">
        	return await _repository.${method.name}(${method.parameters[0].name});
		<#elseif method.operationType == "delete">
        	return await _repository.${method.name}(${method.parameters[0].name});
		</#if>
		}
	<#else>
			// Define default behaviour
		<#if method.operationType == "create">
		public async Task<${method.returnType}> ${method.name}(${serviceClass.name} entity) 
		{
        	return await _repository.${method.name}(entity);
        }
		<#elseif method.operationType == "read">
		public async Task<${method.returnType}> ${method.name}(int id) 
		{
        	return await _repository.${method.name}(id);
       	}
		<#elseif method.operationType == "readAll">
		public async Task<${method.returnType}> ${method.name}() 
		{
        	return await _repository.${method.name}();
       	}
		<#elseif method.operationType == "update">
		public async Task<${method.returnType}> ${method.name}(${serviceClass.name} entity) 
		{
        	return await _repository.${method.name}(entity);
	    }
		<#elseif method.operationType == "delete">
		public async Task ${method.name}(int id) 
		{
        	return await _repository.${method.name}(id);
		}
		</#if>
		</#if>
	</#list>
	}
}