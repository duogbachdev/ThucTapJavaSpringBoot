package com.example.learnspringboot.dto.request;

import com.example.learnspringboot.dto.validator.EnumPattern;
import com.example.learnspringboot.dto.validator.EnumValue;
import com.example.learnspringboot.dto.validator.GenderSubset;
import com.example.learnspringboot.dto.validator.PhoneNumber;
import com.example.learnspringboot.model.Address;
import com.example.learnspringboot.utils.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.example.learnspringboot.utils.Gender.*;

@Getter
@Setter
// implements Serializable để quá trình chuyển đổi từ JSON sang bite nhị phân, quá trình request, response
public class UserRequestDTO implements Serializable {

    @NotBlank(message = "firstName must be not blank") // Khong cho phep gia tri blank
    private String firstName;

    @NotNull(message = "lastName must be not null") // Khong cho phep gia tri null
    private String lastName;

    @Email(message = "email invalid format") // Chi chap nhan nhung gia tri dung dinh dang email
    private String email;

    //@Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    //@Pattern(regexp = "^male|female|other$", message = "gender must be one in {male, female, other}")
    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;

    @NotNull(message = "username must be not null")
    private String username;

    @NotNull(message = "password must be not null")
    private String password;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    private String type;

    @NotEmpty(message = "addresses can not empty")
    private Set<Address> addresses;

    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
    private UserStatus status;

    public UserRequestDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

//    public static class Address {
//        private String apartmentNumber;
//        private String floor;
//        private String building;
//        private String streetNumber;
//        private String street;
//        private String city;
//        private String country;
//        private Integer addressType;
//    }

}
