package com.translation.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    disableSubMappingMethodsGeneration = true
)
public class MapStructConfig {
    // Configuration class for MapStruct
}
