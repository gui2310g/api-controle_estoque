package com.example.api.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorData {
    public static String convertDatetoDateHour(Date data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(data);
    }
}
