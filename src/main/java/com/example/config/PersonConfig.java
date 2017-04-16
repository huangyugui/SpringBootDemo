package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix="com.person")
@Getter
@Setter
@ToString
@Component
public class PersonConfig {
    
    private String name;
    private Integer age;
    
}
