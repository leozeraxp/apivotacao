package com.apivotacao.apivotacao.utils;

import java.util.Objects;

public class Utils {

    public static boolean isNotValid(String valid) {
        return Objects.isNull(valid) || valid.isBlank();
    }
}
