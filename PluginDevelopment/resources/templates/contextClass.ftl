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
<#list classes as classObj>

            modelBuilder.Entity<${classObj.class.name}>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.ToTable("${classObj.class.name}");
<#list classObj.class.properties as property>
	<#if property.referenced>
        <#if property.relationshipAnnotation?? && property.relationshipAnnotation == "OneToOne">
                entity.HasOne(s => s.${property.name})
                <#if property.oppositeProperty.name != "">
                    .WithOne(s => s.${property.oppositeProperty.name})
                </#if>
                    .HasForeignKey("${property.oppositeProperty.type}Id");
        <#elseif property.relationshipAnnotation?? && property.relationshipAnnotation == "OneToMany">
                entity.HasOne(s => s.${property.name})
                <#if property.oppositeProperty.name != "">
                    .WithMany(e => e.${property.oppositeProperty.name})
                </#if>
                    .HasForeignKey("${property.type}Id");
        <#elseif property.relationshipAnnotation?? && property.relationshipAnnotation == "ManyToOne">
                entity.HasMany(s => s.${property.name})
                <#if property.oppositeProperty.name != "">
                    .WithOne(e => e.${property.oppositeProperty.name})
                </#if>
                    .HasForeignKey("${property.oppositeProperty.type}Id");
        <#elseif property.relationshipAnnotation?? && property.relationshipAnnotation == "ManyToMany">
                entity.HasMany(s => s.${property.name})
                    .WithMany(e => e.${property.oppositeProperty.name})
                    .UsingEntity(
                        j =>
                        {
                            j.Property("${property.oppositeProperty.name}Id").HasColumnName("${property.oppositeProperty.name}ForeignKey");
                            j.Property("${property.name}Id").HasColumnName("${property.name}ForeignKey");
                        });
        </#if>
	<#elseif property.upper == 1 >
                entity.Property(p => p.${property.name});
    <#else>
    	<#list 1..property.upper as i>
                entity.Property(p => p.${property.name}${i});
		</#list>  
    </#if> 

</#list>
            });


</#list>
        
        }
    }
}
	