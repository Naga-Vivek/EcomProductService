package com.scaler.EcomProductService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

// To Demonstrate Type Erasures for Backward Compatibility
// Type Erasures remove the internal type of Generics at run time.
public class DemoMain {
    public static void main(String[] args) {
/*        List<Integer>  numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        //numbers.add("hello"); Gives compile time error
        System.out.println(numbers.getClass().getName());

        HashSet<String> words = new HashSet<>();
        words.add("nice");
        words.add("cool");
        //words.add(8);
        System.out.println(words.getClass().getName());*/
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}
