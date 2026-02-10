package com.example.salemanagement.controller;

import com.example.salemanagement.dto.request.CreateStoreRequest;
import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/store")
@Tag(name = "Store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<StoreResponse>> getMyStore() {
        return ResponseEntity.ok(storeService.getMyStore());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StoreResponse>> updateStore(@PathVariable Long id, @ModelAttribute @RequestBody CreateStoreRequest request) {
        return ResponseEntity.ok(storeService.updateStoreStatus(id, request));
    }
}
