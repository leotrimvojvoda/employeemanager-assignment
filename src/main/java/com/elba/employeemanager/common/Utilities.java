package com.elba.employeemanager.common;

import org.springframework.stereotype.Service;

@Service
public class Utilities {

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
