package com.elba.employeemanager.services;

import com.elba.employeemanager.common.Base64ToFile;
import com.elba.employeemanager.models.XlsxDto;
import com.poiji.bind.Poiji;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final Base64ToFile base64ToFile;

    public EmployeeService(Base64ToFile base64ToFile) {
        this.base64ToFile = base64ToFile;
    }

    public ResponseEntity<?> saveEmployees(String xlsxFile) {


        File file = base64ToFile.getFile(xlsxFile, "employees.xlsx");

        try (Workbook workbook = new XSSFWorkbook(file)) {


            List<XlsxDto> employees = Poiji.fromExcel(file, XlsxDto.class);

            employees.forEach(e -> System.out.println(e.getFullName()));


//            Sheet sheet = workbook.getSheetAt(0);
//
//            Row rw = sheet.getRow(0);
//
//
//            for (Cell cell : rw) {
//                switch (cell.getCellType()) {
//                    case STRING -> System.out.print(cell.getStringCellValue()+", ");
//                    case NUMERIC -> System.out.print(cell.getNumericCellValue()+", ");
//                }
//            }

//            for (Row row : sheet) {
//                for (Cell cell : row) {
//                    System.out.println(cell.getStringCellValue());
//                    switch (cell.getCellType()) {
//                        case STRING -> System.out.println("String");
//                        case NUMERIC -> System.out.println("Numeric");
//                        case BOOLEAN -> System.out.println("BOOELAN");
//                        case FORMULA -> System.out.println("FORMULA");
//                    }
//                }
//            }


        } catch (Exception e) {
            System.out.println("Error while parsing file");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //if (file.exists()) file.delete();
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
