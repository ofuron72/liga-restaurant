package com.liga.converter;

import com.liga.entities.WaiterMenuItem;
import com.liga.dto.WaiterMenuItemResponse;
import org.mapstruct.Mapper;

/**
 * Mapper объекта {@link WaiterMenuItem} в {@link WaiterMenuItemResponse}
 */
@Mapper
public interface WaiterMenuDtoToResponseMapper {
    WaiterMenuItemResponse map(WaiterMenuItem dto);
}
