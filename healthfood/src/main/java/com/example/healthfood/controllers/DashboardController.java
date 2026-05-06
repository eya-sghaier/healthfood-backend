package com.example.healthfood.controllers;

import com.example.healthfood.DTO.DashboardDTO;
import com.example.healthfood.services.interfaces.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardDTO getDashboard(Authentication auth) {

        String email = auth.getName();

        return dashboardService.getDashboard(email);
    }
}
