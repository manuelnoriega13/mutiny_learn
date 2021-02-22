package com.manoriega.main;

import com.manoriega.main.model.Auto;
import com.manoriega.main.repo.AutoRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.java.Log;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
public class MutinyMulti {

    public static void main(String[] args) {

        AutoRepository autoRepository = new AutoRepository();
        List<Auto> mayor2000 = autoRepository.all();

        /**basico */
        Multi.createFrom().items(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .select().where(i -> i > 2)
                .onItem().transform(integer -> integer * 2)
                .subscribe()
                .with(integer -> {
                    System.out.println(integer);
//                    log.info("" + integer);
                });

        Multi<List<Auto>> autoMulti1 = Multi
                .createFrom()
                .items(autoRepository.all());

        autoMulti1
                .onFailure()
                .retry()
                .atMost(3)
                .subscribe()
                .with(autos -> log.info(""+autos));

        List<String> namesList = new ArrayList<>();
        namesList.add("manuel");
        namesList.add("alejandro");
        namesList.add("carlos");
        namesList.add("olga");



        Uni<List<String>> listUni = Uni
                .createFrom()
                .item(namesList);

        listUni
                .subscribe()
                .with(names -> System.out.println(
                        names.stream()
                                .filter(name -> !name.equals("manuel"))
                                .map(name -> name.toUpperCase())
                                .collect(Collectors.toList()))
                );
        System.out.println("FIN");
    }
}
