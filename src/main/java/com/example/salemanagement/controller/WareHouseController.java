package com.example.salemanagement.controller;

import com.example.salemanagement.dto.request.CreateWareHouseRequest;
import com.example.salemanagement.dto.request.UpdateWareHouseRequest;
import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.WareHouseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@Tag(name = "Warehouse")
@PreAuthorize("hasRole('ADMIN')")
public class WareHouseController {
    private final WareHouseService wareHouseService;

    public WareHouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<WareHouseResponse>>> getAllWareHouse() {
        return ResponseEntity.ok(wareHouseService.getAllWareHouse());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WareHouseResponse>> createWareHouse(@RequestBody CreateWareHouseRequest request) {
        return ResponseEntity.ok(wareHouseService.createWareHouse(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WareHouseResponse>> getWareHouseById(@PathVariable Long id) {
        return ResponseEntity.ok(wareHouseService.getWareHouseById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<WareHouseResponse>> updateWareHouse(@PathVariable Long id, @RequestBody UpdateWareHouseRequest request) {
        return ResponseEntity.ok(wareHouseService.updateWareHouse(id, request));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteWareHouse(@PathVariable Long id) {
        return wareHouseService.deleteWarehouse(id);
    }
}
