package com.liga.converter;

import com.liga.entities.WaiterMenuItemEntity;
import com.liga.dto.WaiterMenuItemResponse;
import org.mapstruct.Mapper;

/**
 * Mapper объекта {@link WaiterMenuItemEntity} в {@link WaiterMenuItemResponse}
 */
@Mapper
public interface WaiterMenuDtoToResponseMapper {
    WaiterMenuItemResponse map(WaiterMenuItemEntity dto);
}
