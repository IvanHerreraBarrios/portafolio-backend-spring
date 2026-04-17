package com.example.portafolio_backend.service;

import com.example.portafolio_backend.controller.mapper.IOrderMapper;
import com.example.portafolio_backend.domain.dto.response.OrderResponse;
import com.example.portafolio_backend.persistance.entity.OrderEntity;
import com.example.portafolio_backend.service.interfaces.IOrderReportService;
import com.example.portafolio_backend.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderReportService implements IOrderReportService {
    private final IOrderService orderService;
    private final IOrderMapper mapper;

    public byte[] generateOrderPdf(Long orderId, String customerEmail) throws JRException {
        // Obtener la entidad y mapearla a DTO
        OrderEntity orderEntity = orderService.findById(orderId);
        OrderResponse orderResponse = mapper.toOrderResponse(orderEntity);

        // Cargar JRXML
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/order_report_v2.jrxml")
        );

        // Parámetros para el PDF
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderResponse.getId());
        parameters.put("orderStatus", orderResponse.getStatus());
        parameters.put("customerId", orderResponse.getCustomerId());
        parameters.put("customerEmail", customerEmail);
        parameters.put("orderTotal", orderResponse.getTotal());
        parameters.put("creationDate", orderResponse.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // DataSource para la tabla de detalles
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderResponse.getOrderDetails());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }
}
