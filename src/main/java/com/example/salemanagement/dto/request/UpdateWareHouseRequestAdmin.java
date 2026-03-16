package com.example.salemanagement.dto.request;

import com.example.salemanagement.Enum.WareHouseRequestStatus;
import com.example.salemanagement.Enum.WareHouseStatus;
import com.example.salemanagement.entity.Store;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateWareHouseRequestAdmin {
    private WareHouseRequestStatus status;
    private WareHouseStatus wareHouseStatus;
}
