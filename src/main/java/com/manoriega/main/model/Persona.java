package com.manoriega.main.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Persona {
    private String nombre;
    private String apellido;
    private Integer edad;
}
