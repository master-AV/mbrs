namespace Controller 
{	
	[ApiController]
	[Route("api/[controller]")]
	public partial ${controllerClass.name}Controller : ControllerBase
	{
    	private readonly I${controllerClass.name}Service _service;
    	
	    public ${controllerClass.name}Controller(I${controllerClass.name}Service service)
	    {
	        _service = service;
	    }
	    <#list controllerClass.relatedMethods as method>
	<#if method.operationType == "other">
	// type get/put/post/delte endpoint
		<#if method.apiType?? && method.apiEndpoint?? >
		[Http${method.apiType?substring(0,1)?upper_case}${word?substring(1)}("${apiEnpoint}")
		<#else>
		[HttpGet("${method.name}")]
		</#if>
		partial Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>);
	<#elseif method.returnType?? && method.parameters??>
		<#if method.operationType == "create">
		[HttpPost]
		<#elseif method.operationType == "read">
		[HttpGet("{${method.parameters[0].name}}")]
		<#elseif method.operationType == "readAll">
		[HttpGet]
		<#elseif method.operationType == "update">
		[HttpPut("{${method.parameters[0].name}}")]
		<#elseif method.operationType == "delete">
		[HttpDelete("{${method.parameters[0].name}}")]
		</#if>
		public async Task<#if method.returnType??><${method.returnType}></#if> ${method.name}([FromBody] <#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>) 
		{
		<#if method.operationType == "create">
			await _context.${controllerClass.name}s.AddAsync(${method.parameters[0].name});
        	await _context.SaveChangesAsync();
		<#elseif method.operationType == "read">
        	return await _context.${controllerClass.name}s.FindAsync(${method.parameters[0].name});
		<#elseif method.operationType == "readAll">
        	return await _context.${controllerClass.name}s.ToListAsync();
		<#elseif method.operationType == "update">
	        _context.${controllerClass.name}s.Update(${method.parameters[0].name});
	        await _context.SaveChangesAsync();
		<#elseif method.operationType == "delete">
			var entity = await _context.${controllerClass.name}s.FindAsync(${method.parameters[0].name});
	        if (entity != null)
		    {
		        _context.${controllerClass.name}s.Remove(entity);
	            await _context.SaveChangesAsync();
	        }
		</#if>
		}
	<#else>
			// Define default behaviour
		<#if method.operationType == "create">
		[HttpPost]
		public async Task<${method.returnType}> ${method.name}([FromMethod] ${controllerClass.name} entity) 
		{
			await _context.${controllerClass.name}s.AddAsync(entity);
       		await _context.SaveChangesAsync();
        }
		<#elseif method.operationType == "read">
		[HttpGet({id})]
		public async Task<${method.returnType}> ${method.name}(int id) 
		{
       		return await _context.${controllerClass.name}s.FindAsync(id);
       	}
		<#elseif method.operationType == "readAll">
		[HttpGet]
		public async Task<${method.returnType}> ${method.name}() 
		{
       		return await _context.${controllerClass.name}s.ToListAsync();
       	}
		<#elseif method.operationType == "update">
		[HttpPut({id})]
		public async Task<${method.returnType}> ${method.name}(${controllerClass.name} entity) 
		{
		    _context.${controllerClass.name}s.Update(entity);
		    await _context.SaveChangesAsync();
	    }
		<#elseif method.operationType == "delete">
		[HttpDelete({id})]
		public async Task ${method.name}(int id) 
		{
			var entity = await _context.${controllerClass.name}s.FindAsync(id);
		    if (entity != null)
		    {
	            _context.${controllerClass.name}s.Remove(entity);
	            await _context.SaveChangesAsync();
		    }
		}
		</#if>
		</#if>
	</#list>
	    
	}
}