package com.example.portafolio_backend.controller;

import com.example.portafolio_backend.controller.mapper.ICakeMapper;
import com.example.portafolio_backend.domain.dto.request.CakeRequest;
import com.example.portafolio_backend.domain.dto.response.CakeResponse;
import com.example.portafolio_backend.service.interfaces.ICakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cakes")
public class CakeController {
    private final ICakeService service;
    private final ICakeMapper mapper;

    @GetMapping("/v1/api")
    public List<CakeResponse> findAll() {
        return mapper.toCakeResponseList(service.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public CakeResponse findById(@PathVariable Long id) {
        return mapper.toCakeResponse(service.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<CakeResponse> save(@Valid @RequestBody CakeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toCakeResponse(
                        service.save(mapper.toCakeEntity(request))));
    }

    @PutMapping("/v1/api/{id}")
    public CakeResponse update(@PathVariable Long id, @Valid @RequestBody CakeRequest request) {
        return mapper.toCakeResponse(
                service.update(mapper.toCakeEntity(request), id));
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
