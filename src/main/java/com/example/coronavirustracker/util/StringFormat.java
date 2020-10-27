package com.example.coronavirustracker.util;

import java.text.NumberFormat;
import java.util.Locale;

public class StringFormat {
    public static String numberFormat(int number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
}
