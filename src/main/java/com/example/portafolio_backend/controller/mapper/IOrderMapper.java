package com.example.portafolio_backend.controller.mapper;

import com.example.portafolio_backend.domain.dto.request.OrderDetailRequest;
import com.example.portafolio_backend.domain.dto.request.OrderRequest;
import com.example.portafolio_backend.domain.dto.response.OrderDetailResponse;
import com.example.portafolio_backend.domain.dto.response.OrderResponse;
import com.example.portafolio_backend.domain.dto.response.OrderSaveResponse;
import com.example.portafolio_backend.persistance.entity.OrderDetailEntity;
import com.example.portafolio_backend.persistance.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "cake", ignore = true)
    @Mapping(target = "id.idCake", source = "subId")
    OrderDetailEntity toOrderDetailEntity(OrderDetailRequest request);

    @Mapping(target = "subId", source = "id.idCake")
    @Mapping(target = "nameCake", source = "cake.name")
    OrderDetailResponse toOrderDetailResponse(OrderDetailEntity entity);

    List<OrderDetailResponse> toOrderDetailResponseList(List<OrderDetailEntity> entityList);

    // 🔹 Mapear todos los campos explícitamente
    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "orderDetails", source = "orderDetails")
    OrderResponse toOrderResponse(OrderEntity entity);

    List<OrderResponse> toOrderResponseList(List<OrderEntity> entityList);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "customer", ignore = true)
    OrderEntity toOrderEntity(OrderRequest request);

    OrderSaveResponse toOrderSaveResponse(OrderEntity entity);

}
