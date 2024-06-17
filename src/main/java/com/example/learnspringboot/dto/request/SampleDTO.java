package com.example.learnspringboot.dto.request;


import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SampleDTO implements Serializable {
    private Integer id;
    private String name;
}
