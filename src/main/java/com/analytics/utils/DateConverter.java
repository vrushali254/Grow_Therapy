package com.analytics.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateConverter {
    //Format YYYYMMDD
    final static DateTimeFormatter basicFormatter = DateTimeFormatter.BASIC_ISO_DATE;

    final static DateTimeFormatter localFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    public static String convertDateToString(LocalDate date) {
        return basicFormatter.format(date).toString();
    }

    public static LocalDate convertStringToDate(String date) {
        try {
            return LocalDate.parse(date, basicFormatter);
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input format found: " + e.getMessage());
        }
    }

    public static String convertLocalDateToString(LocalDate date) {return localFormatter.format(date).toString();}
    public static String addMonths(String date, Long numMonths) {
       LocalDate endDate = convertStringToDate(date).plusMonths(numMonths);
       return convertDateToString(endDate);
    }

    public static String addWeeks(String date, Long numWeeks) {
        LocalDate endDate = convertStringToDate(date).plusWeeks(numWeeks);
        return convertDateToString(endDate);
    }

    public static List<String> getDaysInWeek(String year, String month, String date) {
        List<String> nextDays = new ArrayList<>();
        StringBuilder startDate = new StringBuilder();
        startDate.append(year).append(month).append(date);
        // Store all the days in a week in YYYY-MM-DD format
        for(Long i=0L; i<7L; i++) {
            LocalDate nextDate = convertStringToDate(startDate.toString()).plusDays(i);
            nextDays.add(convertLocalDateToString(nextDate));

        }
        return nextDays;
    }

    //Split the date in YYYY-MM-DD format and get individual values
    public static String[] splitDate(String date) {
        String[] dateTokens = date.split("[-]");
        if(dateTokens.length==3) {
            return dateTokens;
        }
        return null;
    }
}
