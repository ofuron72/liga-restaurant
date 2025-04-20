package com.liga.converter;

import com.liga.dto.WaiterMenuItemDto;
import com.liga.dto.WaiterMenuItemResponse;
import org.mapstruct.Mapper;

/**
 * Mapper объекта {@link WaiterMenuItemDto} в {@link WaiterMenuItemResponse}
 */
@Mapper
public interface WaiterMenuDtoToResponseMapper {
    WaiterMenuItemResponse map(WaiterMenuItemDto dto);
}
