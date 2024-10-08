package com.codewithali.bnmanagement.controller;

import com.codewithali.bnmanagement.dto.ManagementDto;
import com.codewithali.bnmanagement.service.ManagementService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/v1/api/management")
public class ManagementController {
    private final ManagementService managementService;
    private final ResourceLoader resourceLoader;

    public ManagementController(ManagementService managementService, ResourceLoader resourceLoader) {
        this.managementService = managementService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/getManagementsWithMonth")
    public ResponseEntity<List<ManagementDto>> getManagementsWithMonth(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(managementService.getAllManagementWithMonth(month,year));
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        try {
            Resource resource = resourceLoader.getResource("file:uploads/"+filename);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = Files.probeContentType(resource.getFile().toPath());
            return ResponseEntity.ok()
                    .contentType(contentType != null ? MediaType.parseMediaType(contentType): MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }

    }
}
