package com.manoriega.main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Auto {
    private String marca;
    private String modelo;
    private String color;
    private Integer ano;
}
