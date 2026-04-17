package com.example.portafolio_backend.controller;

import com.example.portafolio_backend.controller.mapper.ICustomerMapper;
import com.example.portafolio_backend.domain.dto.request.CustomerRequest;
import com.example.portafolio_backend.domain.dto.response.CustomerResponse;
import com.example.portafolio_backend.domain.dto.response.CustomerUpdateResponse;
import com.example.portafolio_backend.domain.dto.response.CutomerRepsonseList;
import com.example.portafolio_backend.service.interfaces.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final ICustomerService service;
    private final ICustomerMapper mapper;

    @GetMapping("/v1/api")
    public List<CutomerRepsonseList> findAll() {
        return mapper.toCustomerResponseList(service.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return mapper.toCustomerResponse(service.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<CustomerResponse> save(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toCustomerResponse(
                        service.save(mapper.toCustomerEntity(request))));
    }

    @PutMapping("/v1/api/{id}")
    public CustomerUpdateResponse update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return service.update(mapper.toCustomerEntity(request), id);
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
