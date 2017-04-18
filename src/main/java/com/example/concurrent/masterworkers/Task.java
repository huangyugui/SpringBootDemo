package com.example.concurrent.masterworkers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Task {

    private int id;
    private String name;
    private int age;
}
