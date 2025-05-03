package io.vinta.containerbase.common.mapstruct;

import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;

@MapperConfig(builder = @Builder(disableBuilder = false), mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface MapstructConfig {

}