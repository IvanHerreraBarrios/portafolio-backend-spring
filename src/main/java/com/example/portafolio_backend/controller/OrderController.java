package com.example.portafolio_backend.controller;

import com.example.portafolio_backend.controller.mapper.IOrderMapper;
import com.example.portafolio_backend.domain.dto.request.OrderRequest;
import com.example.portafolio_backend.domain.dto.response.OrderResponse;
import com.example.portafolio_backend.service.interfaces.IOrderReportService;
import com.example.portafolio_backend.service.interfaces.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService service;
    private final IOrderMapper mapper;
    private final IOrderReportService reportService;

    @GetMapping("/v1/api")
    public List<OrderResponse> findAll() {
        return mapper.toOrderResponseList(service.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return mapper.toOrderResponse(service.findById(id));
    }

    @GetMapping("/v1/api/customer/{idCustomer}/orders")
    public List<OrderResponse> findAllByIdCustomer(@PathVariable Long idCustomer) {
        return mapper.toOrderResponseList(service.findAllByCustomerId(idCustomer));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<OrderResponse> save(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toOrderResponse(
                        service.save(mapper.toOrderEntity(request))));
    }



    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Generar PDF de la orden")
    @ApiResponse(
            responseCode = "200",
            description = "PDF generado",
            content = @Content(
                    mediaType = "application/pdf",
                    schema = @Schema(type = "string", format = "binary")
            )
    )
    public ResponseEntity<byte[]> getOrderPdf(@PathVariable Long id,
                                              @RequestParam String email) throws Exception {

        // Generar PDF
        byte[] pdfBytes = reportService.generateOrderPdf(id, email);

        // Retornar respuesta
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF) // 👈 CLAVE para Swagger
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order_" + id + ".pdf")
                .body(pdfBytes);
    }




    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
