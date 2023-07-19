package project.cnu.daehakro.domain.enums;

import java.util.Arrays;

public enum Department {
    CSE,
    NAOE,
    PIANO,

    DEFAULT;
    public static Department of(String department) {
        return Arrays.stream(values())
                .filter(s -> s.name().equals(department))
                .findFirst()
                .orElse(DEFAULT);
    }
}
