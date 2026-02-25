package com.example.salemanagement.controller;

import com.example.salemanagement.dto.request.CreateInventoryRequest;
import com.example.salemanagement.dto.request.UpdateInventoryRequest;
import com.example.salemanagement.dto.response.InventoryResponse;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllWareHouse() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InventoryResponse>> createWareHouse(@RequestBody CreateInventoryRequest request) {
        return ResponseEntity.ok(inventoryService.createInventory(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InventoryResponse>> updateInventoryById(@PathVariable Long id, @ModelAttribute @RequestBody UpdateInventoryRequest request) {
        return ResponseEntity.ok(inventoryService.updateInventoryById(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.deleteInventoryById(id));
    }
}
