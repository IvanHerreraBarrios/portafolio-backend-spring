package com.example.portafolio_backend.controller.mapper;

import com.example.portafolio_backend.domain.dto.request.CustomerRequest;
import com.example.portafolio_backend.domain.dto.response.CustomerResponse;
import com.example.portafolio_backend.domain.dto.response.CustomerUpdateResponse;
import com.example.portafolio_backend.domain.dto.response.CutomerRepsonseList;
import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "role", ignore = true)
    CustomerEntity toCustomerEntity(CustomerRequest request);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "numberCellphone", source = "numberCellphone")
    CustomerResponse toCustomerResponse(CustomerEntity entity);

    //CustomerUpdateResponse toCustomerUpdateResponse(CustomerEntity entity);

    List<CutomerRepsonseList> toCustomerResponseList(List<CustomerEntity> customerEntityList);
}
