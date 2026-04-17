package com.example.portafolio_backend.controller.mapper;

import com.example.portafolio_backend.domain.dto.request.CakeRequest;
import com.example.portafolio_backend.domain.dto.response.CakeResponse;
import com.example.portafolio_backend.persistance.entity.CakeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICakeMapper {

    @Mapping(target = "brandDescription", source = "brand.description")
    CakeResponse toCakeResponse(CakeEntity entity);

    List<CakeResponse> toCakeResponseList(List<CakeEntity> cakeEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    CakeEntity toCakeEntity(CakeRequest request);
}
