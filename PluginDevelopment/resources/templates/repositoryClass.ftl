namespace Repo 
{
	public I${repoClass.name}Repository
	{
	<#list repoClass.relatedMethods as method>
		Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>);
	</#list>  
	}
	
	public partial ${repoClass.name}Repository : I${repoClass.name}Repository
	{
    	private readonly DataContext _context;
    	
    	public ${repoClass.name}Repository(DataContext context)
	    {
	        _context = context;
	    }
    
<#list repoClass.relatedMethods as method>
	<#if method.operationType == "other">
		partial Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>);
	<#elseif method.returnType?? && method.parameters??>
		public async Task<#if method.returnType??><${method.returnType}></#if> ${method.name}(<#list method.parameters as p><#if p.direction == "OUT">out</#if>${p.typePackage} ${p.name}<#if !p?is_last>,</#if></#list>) 
		{
		<#if method.operationType == "create">
			await _context.${repoClass.name}s.AddAsync(${method.parameters[0].name});
        	await _context.SaveChangesAsync();
		<#elseif method.operationType == "read">
        	return await _context.${repoClass.name}s.FindAsync(${method.parameters[0].name});
		<#elseif method.operationType == "readAll">
        	return await _context.${repoClass.name}s.ToListAsync();
		<#elseif method.operationType == "update">
	        _context.${repoClass.name}s.Update(${method.parameters[0].name});
	        await _context.SaveChangesAsync();
		<#elseif method.operationType == "delete">
			var entity = await _context.${repoClass.name}s.FindAsync(${method.parameters[0].name});
	        if (entity != null)
		    {
		        _context.${repoClass.name}s.Remove(entity);
	            await _context.SaveChangesAsync();
	        }
		</#if>
		}
	<#else>
			// Define default behaviour
		<#if method.operationType == "create">
		public async Task<${method.returnType}> ${method.name}(${repoClass.name} entity) 
		{
			await _context.${repoClass.name}s.AddAsync(entity);
       		await _context.SaveChangesAsync();
        }
		<#elseif method.operationType == "read">
		public async Task<${method.returnType}> ${method.name}(int id) 
		{
       		return await _context.${repoClass.name}s.FindAsync(id);
       	}
		<#elseif method.operationType == "readAll">
		public async Task<${method.returnType}> ${method.name}() 
		{
       		return await _context.${repoClass.name}s.ToListAsync();
       	}
		<#elseif method.operationType == "update">
		public async Task<${method.returnType}> ${method.name}(${repoClass.name} entity) 
		{
		    _context.${repoClass.name}s.Update(entity);
		    await _context.SaveChangesAsync();
	    }
		<#elseif method.operationType == "delete">
		public async Task ${method.name}(int id) 
		{
			var entity = await _context.${repoClass.name}s.FindAsync(id);
		    if (entity != null)
		    {
	            _context.${repoClass.name}s.Remove(entity);
	            await _context.SaveChangesAsync();
		    }
		}
		</#if>
		</#if>
	</#list>
	
	<#list regions?keys as key>
		// <protected id="${key}" begin>
		 ${regions[key]}
		// <protected id="${key}" end>
	</#list>
	}
}