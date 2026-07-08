package com.peerrank.peerrank.controller;

import com.peerrank.peerrank.dto.DashboardDTO;
import com.peerrank.peerrank.response.ApiResponse;
import com.peerrank.peerrank.response.ApiResponseBuilder;
import com.peerrank.peerrank.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardDTO>> getDashboard() {

        ApiResponse<DashboardDTO> response =
                ApiResponseBuilder.success(
                        "Dashboard fetched successfully",
                        dashboardService.getDashboard()
                );

        return ResponseEntity.ok(response);
    }
}