package com.example.nadezhdavachevaemployees.util;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.opencsv.CSVReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class CSVParser {

    public static List<EmployeeData> parseCSV(MultipartFile file) {
        List<EmployeeData> employeeData = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                if (line[0].trim().isEmpty() || line[1].trim().isEmpty()) {
                    continue;
                }
                int employeeId = Integer.parseInt(line[0]);
                int projectId = Integer.parseInt(line[1]);
                Date dateFrom = dateFormat.parse(line[2]);
                Date dateTo = line[3].trim().equals("NULL") ? new Date() : dateFormat.parse(line[3]);
                employeeData.add(new EmployeeData(employeeId, projectId, dateFrom, dateTo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeData;
    }
}
