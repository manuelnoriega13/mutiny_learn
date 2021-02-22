package com.manoriega.main.repo;

import com.manoriega.main.model.Auto;

import java.util.ArrayList;
import java.util.List;

public class AutoRepository {

    private List<Auto> autoList;

    public List<Auto> all() {
        autoList = new ArrayList<>();

        autoList.add(Auto.builder()
                .modelo("Aveo")
                .marca("Chevy")
                .color("Azul")
                .ano(2010)
                .build());

        autoList.add(Auto.builder()
                .modelo("Laser")
                .marca("Ford")
                .color("Blanco")
                .ano(2002)
                .build());

        autoList.add(Auto.builder()
                .modelo("Yaris")
                .marca("Toyota")
                .color("Gris")
                .ano(2008)
                .build());

        autoList.add(Auto.builder()
                .modelo("Corola")
                .marca("Toyota")
                .color("Azul")
                .ano(1998)
                .build());

        autoList.add(Auto.builder()
                .modelo("Corola")
                .marca("Toyota")
                .color("Verde")
                .ano(2001)
                .build());

        return autoList;
    }
}
