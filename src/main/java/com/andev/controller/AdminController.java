package com.andev.controller;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    @RolesAllowed({"ADMIN"})
    @GetMapping("/vip")
    public String zonVip() {
        return "zoneVip";
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/normal")
    public String zoneNormal() {
        return "zoneNormal";
    }

    @GetMapping("/info")
    public Authentication getInfoUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}