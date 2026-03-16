package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateCustomerRequest;
import com.example.salemanagement.dto.response.CustomerResponse;
import com.example.salemanagement.response.ApiResponse;

import java.util.List;

public interface CustomerService {
    ApiResponse<List<CustomerResponse>> getAllCustomer();
    ApiResponse<CustomerResponse> createCustomer(Long storeId, CreateCustomerRequest request);
}
