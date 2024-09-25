package com.codewithali.bnmanagement.controller;

import com.codewithali.bnmanagement.dto.ManagementDto;
import com.codewithali.bnmanagement.dto.UserDto;
import com.codewithali.bnmanagement.dto.request.CreateUserRequest;
import com.codewithali.bnmanagement.dto.response.SuccessReponse;
import com.codewithali.bnmanagement.model.Role;
import com.codewithali.bnmanagement.service.AuthService;
import com.codewithali.bnmanagement.service.ManagementService;
import com.codewithali.bnmanagement.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    private final ManagementService managementService;
    private final UserService userService;
    private final AuthService authService;

    public AdminController(ManagementService managementService, UserService userService, AuthService authService) {
        this.managementService = managementService;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<SuccessReponse> addManagement(@RequestPart("managementJson") String req,
                                                        @RequestParam(value = "file",required = false) MultipartFile file)
            throws JsonProcessingException {
        return ResponseEntity.ok(managementService.createManagement(req,file));
    }

    @PutMapping
    public ResponseEntity<SuccessReponse> updateManagement(@RequestBody ManagementDto req) {
        return ResponseEntity.ok(managementService.updateManagement(req));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessReponse> deleteManagement(@PathVariable String id) {
        return ResponseEntity.ok(managementService.deleteManagement(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/changeRole/{id}")
    public ResponseEntity<SuccessReponse> changeUserRole(@PathVariable String id,@RequestBody Set<Role> role) {
        return ResponseEntity.ok(userService.changeUserRole(id,role));
    }

    @PostMapping("/createUser")
    public ResponseEntity<SuccessReponse> createUser(@RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(authService.registerUser(req));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<SuccessReponse> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
