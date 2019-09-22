package com.jojos.home.datetime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author gkaranikas
 */
public class Parser {
    private static final DateTimeFormatter POTENTIAL_FORMATS = DateTimeFormatter.ofPattern("[HH:mm:ss][HH:mm]");
    private static final DateTimeFormatter POTENTIAL_DATE_FORMATS = DateTimeFormatter.ofPattern("[yyyy_MM_dd][yyyy-MM-dd]");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("[yyyy_MM_dd]");

    /**
     * This function attempts to parse a time. Supporting the use of potentially multiple formats from a single place.
     *
     * @param string the time
     * @return a LocalTIme object
     */
    public static LocalTime parseLocalTime(String string) {
        return LocalTime.parse(string, POTENTIAL_FORMATS);
    }

    public static String formatLocalDate(LocalDate localDate) {
        return localDate.format(DATE_FORMAT);
    }

    public static LocalDate parseLocalDate(String string) {
        return LocalDate.parse(string, POTENTIAL_DATE_FORMATS);
    }


    public static String getTodaysDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd-HHmmss");
        return sdf.format(date);
    }
}
