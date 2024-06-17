package com.example.learnspringboot.service;

import com.example.learnspringboot.dto.request.UserRequestDTO;
import com.example.learnspringboot.dto.response.PageResponse;
import com.example.learnspringboot.dto.response.UserDetailResponse;
import com.example.learnspringboot.utils.UserStatus;

import java.util.List;

public interface UserService {
    long createUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(long userId);

    UserDetailResponse getUser(long userId);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, String... sortBy);

}
