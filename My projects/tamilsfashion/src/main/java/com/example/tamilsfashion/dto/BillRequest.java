package com.example.tamilsfashion.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {

    private Long customerId;

    private List<BillItemRequest> items;

}