using Microsoft.EntityFrameworkCore;	

namespace Model
{
    public class DatabaseContext : DbContext
    {
    
<#list classes as classObj>
        public virtual DbSet<${(classObj.class.name)!"Default"}> ${(classObj.class.name)!"Default"}s { get; set; }
</#list>

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
<#list relationships as relationship>
            modelBuilder.Entity<${relationship.firstAttributeType}>()
        <#if relationship.type == "OneToOne">
                .HasOne(s => s.${relationship.secondAttributeName})
                .WithOne(x => x.${relationship.firstAttributeName})
        	<#if relationship.columnName?? >
        		.HasForeignKey<${relationship.firstAttributeType}>("${relationship.columnName}")<#if !relationship.deleteBehavior?? >;</#if>
        	<#else>
                .HasForeignKey<${relationship.firstAttributeType}>("${relationship.secondAttributeType}Id")<#if !relationship.deleteBehavior?? >;</#if>
            </#if>
            <#if relationship.deleteBehavior?? >
            	.OnDelete(DeleteBehaviour.${relationship.deleteBehavior});
            </#if>
                    
        <#elseif relationship.type == "OneToMany">
                .HasMany(s => s.${relationship.secondAttributeName})
                .WithOne(x => x.${relationship.firstAttributeName})
        	<#if relationship.columnName?? >
        		.HasForeignKey("${relationship.columnName}")<#if !relationship.deleteBehavior?? >;</#if>
        	<#else>
                .HasForeignKey("${relationship.firstAttributeType}Id")<#if !relationship.deleteBehavior?? >;</#if>
            </#if>
            <#if relationship.deleteBehavior?? >
            	.OnDelete(DeleteBehaviour.${relationship.deleteBehavior});
            </#if>
            
        <#elseif relationship.type == "ManyToMany">
        		.HasMany(e => e.${relationship.secondAttributeName})
        		.WithMany(e => e.${relationship.firstAttributeName})<#if !relationship.joinTableName?? >;</#if>
        	<#if relationship.joinTableName?? >
                .UsingEntity(
           			"${relationship.joinTableName}",
	           			r.HasOne(typeof(${relationship.secondAttributeType})).WithMany().HasForeignKey("${relationship.secondAttributeType}Id").HasPrincipalKey(nameof(${relationship.secondAttributeType}.Id)),
						l=>l.HasOne(typeof(${relationship.firstAttributeType})).WithMany().HasForeignKey("${relationship.firstAttributeType}Id").HasPrincipalKey(nameof(${relationship.firstAttributeType}.Id))
						j => j.HasKey("${relationship.firstAttributeType}Id", "${relationship.secondAttributeType}Id"));
            </#if>
        </#if>
</#list>
        }
    }
}