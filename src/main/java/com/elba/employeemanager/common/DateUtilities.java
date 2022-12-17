package com.elba.employeemanager.common;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateUtilities {

    public LocalDate getDate(int date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return LocalDate.parse(String.valueOf(date), dateFormatter);
    }

}
