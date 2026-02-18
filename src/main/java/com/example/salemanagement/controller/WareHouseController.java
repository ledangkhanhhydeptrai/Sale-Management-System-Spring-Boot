package com.example.salemanagement.controller;

import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.WareHouseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@Tag(name = "Warehouse")
@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','STAFF')")
public class WareHouseController {
    private final WareHouseService wareHouseService;

    public WareHouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WareHouseResponse>>> getAllWareHouse() {
        return ResponseEntity.ok(wareHouseService.getAllWareHouse());
    }
}
