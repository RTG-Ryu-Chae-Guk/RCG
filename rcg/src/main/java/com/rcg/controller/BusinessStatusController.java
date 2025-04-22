package com.rcg.controller;

import com.rcg.dto.BusinessStatusRequestDTO;
import com.rcg.dto.BusinessStatusResponseDTO;
import com.rcg.service.BusinessStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/status")
public class BusinessStatusController {

    @Autowired
    private BusinessStatusService businessStatusService;

    @PostMapping
    public ResponseEntity<BusinessStatusResponseDTO> getBusinessStatus(@RequestBody BusinessStatusRequestDTO requestDTO) {
        BusinessStatusResponseDTO responseDTO = businessStatusService.getBusinessStatus(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}