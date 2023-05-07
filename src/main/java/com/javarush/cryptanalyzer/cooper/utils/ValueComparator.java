package com.javarush.cryptanalyzer.cooper.utils;

import java.util.Map;
import java.util.Comparator;

public class ValueComparator implements Comparator<Character> {
    Map<Character, Integer> base;

    public ValueComparator(Map<Character, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(Character o1, Character o2) {
        if (base.get(o1) >= base.get(o2)) {
            return -1;
        } else {
            return 1;
        }
    }
}
