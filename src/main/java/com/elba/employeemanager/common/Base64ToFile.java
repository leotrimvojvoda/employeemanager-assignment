package com.elba.employeemanager.common;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class Base64ToFile {

    public File getFile(String base64, String fileName) {

        byte[] data = Base64.decodeBase64(base64);

        File file = new File(fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
