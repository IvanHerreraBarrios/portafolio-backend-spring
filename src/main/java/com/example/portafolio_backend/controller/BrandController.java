package com.example.portafolio_backend.controller;

import com.example.portafolio_backend.controller.mapper.IBrandMapper;
import com.example.portafolio_backend.domain.dto.request.BrandRequest;
import com.example.portafolio_backend.domain.dto.response.BrandResponse;
import com.example.portafolio_backend.service.interfaces.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final IBrandService service;
    private final IBrandMapper mapper;

    @GetMapping("/v1/api")
    public List<BrandResponse> findAll() {
        return mapper.toBrandResponseList(service.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public BrandResponse findById(@PathVariable Long id) {
        return mapper.toBrandResponse(service.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<BrandResponse> save(@Valid @RequestBody BrandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toBrandResponse(
                        service.save(mapper.toBrandEntity(request))));
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
