package com.example.learnspringboot.controller;

import com.example.learnspringboot.configuration.Translator;
import com.example.learnspringboot.dto.exception.ResourceNotFoundException;
import com.example.learnspringboot.dto.request.UserRequestDTO;
import com.example.learnspringboot.dto.response.ResponseData;
import com.example.learnspringboot.dto.response.ResponseError;
import com.example.learnspringboot.dto.response.UserDetailResponse;
import com.example.learnspringboot.service.UserService;
import com.example.learnspringboot.utils.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create User", description = "API Create New User")
    @PostMapping("/create")
    public ResponseData<Long> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("userRequestDTO: " + userRequestDTO);
        try {
            long userId = userService.createUser(userRequestDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.create.success"), userId);
        } catch (Exception e) {
            log.error("Error creating user = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Cannot create user");
        }
    }

    @Operation(summary = "Update User", description = "API Update New User")
    @PutMapping("/edit/{id}")
    public ResponseData<?> editUser(@PathVariable @Min(value = 1, message = "id must be greater than 0") int id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            userService.updateUser(id, userRequestDTO);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successfully");
        } catch (Exception e) {
            log.error("Error creating user = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Cannot update user");
        }
    }

    @Operation(summary = "Change status of user", description = "Send a request via this API to change status of user")
    @PatchMapping("/edit/{id}")
    public ResponseData<?> changeStatus(@PathVariable int id, @RequestParam(required = false) UserStatus status) {
        try {
            userService.changeStatus(id, status);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User changed successfully");
        } catch (Exception e) {
            log.error("Error creating user = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Cannot update user");
        }
    }

    @Operation(summary = "Delete user permanently", description = "Send a request via this API to delete user permanently")
    @DeleteMapping("/delete/{id}")
    public ResponseData<?> deleteUser(@PathVariable @Min(value = 1, message = "id must be greater than 0") int id) {
        System.out.println("userRequestDTO: " + id);
        try {
            userService.deleteUser(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Change status fail");
        }
    }

    @Operation(summary = "Get user detail", description = "Send a request via this API to get user information")
    @GetMapping("/detail/{id}")
    public ResponseData<UserDetailResponse> getUser(@PathVariable long id) {
        System.out.println("userRequestDTO: " + id);
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "User", userService.getUser(id));
        } catch (ResourceNotFoundException e) {
            log.error("Error getting user = {}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(summary = "Get list of users per pageNo", description = "Send a request via this API to get user list by pageNo and pageSize")
    @GetMapping("/detail/list")
    public ResponseData<?> getAllUser(@RequestParam(required = false) String email, @RequestParam(defaultValue = "0", required = false) int pageNo, @RequestParam(defaultValue = "10", required = false) int pageSize, @RequestParam(required = false) String sortBy) {
        return new ResponseData<>(HttpStatus.OK.value(), "User", userService.getAllUsersWithSortBy(pageNo, pageSize, sortBy));
    }

    @Operation(summary = "Get list of users per pageNo", description = "Send a request via this API to get user list by pageNo and pageSize")
    @GetMapping("/detail/list-detail")
    public ResponseData<?> getAllUsersWithSortByMultipleColumns(@RequestParam(required = false) String email, @RequestParam(defaultValue = "0", required = false) int pageNo, @Min(10) @RequestParam(defaultValue = "10", required = false) int pageSize, @RequestParam(required = false) String... sort) {
        return new ResponseData<>(HttpStatus.OK.value(), "User", userService.getAllUsersWithSortByMultipleColumns(pageNo, pageSize, sort));
    }
}
