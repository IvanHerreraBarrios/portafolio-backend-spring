package com.example.portafolio_backend.controller.mapper;

import com.example.portafolio_backend.domain.dto.request.BrandRequest;
import com.example.portafolio_backend.domain.dto.response.BrandResponse;
import com.example.portafolio_backend.persistance.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandMapper {



    BrandResponse toBrandResponse(BrandEntity entity);

    List<BrandResponse> toBrandResponseList(List<BrandEntity> brandEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cakes", ignore = true)
    BrandEntity toBrandEntity(BrandRequest request);
}
