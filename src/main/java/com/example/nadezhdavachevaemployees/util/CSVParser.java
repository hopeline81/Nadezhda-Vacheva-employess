package com.example.nadezhdavachevaemployees.util;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.opencsv.CSVReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.web.multipart.MultipartFile;

public class CSVParser {

    private static final String[] DATE_FORMATS = {
            "yyyy-MM-dd",
            "MM/dd/yyyy",
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "MM-dd-yyyy",
            "yyyy/MM/dd",
            "dd MMM yyyy",
            "yyyy.MM.dd",
            "dd.MM.yyyy",
            "MMM dd yyyy",
            "dd-MMM-yyyy",
            "yyyyMMdd",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    };

    public static List<EmployeeData> parseCSV(MultipartFile file) {
        List<EmployeeData> employeeData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                if (line[0].trim().isEmpty() || line[1].trim().isEmpty()) {
                    continue;
                }
                int employeeId = Integer.parseInt(line[0]);
                int projectId = Integer.parseInt(line[1]);
                Date dateFrom = parseDate(line[2]);
                Date dateTo = line[3].trim().equalsIgnoreCase("NULL") ? new Date() : parseDate(line[3]);
                employeeData.add(new EmployeeData(employeeId, projectId, dateFrom, dateTo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeData;
    }

    private static Date parseDate(String dateString) throws ParseException {
        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format, Locale.ENGLISH).parse(dateString);
            } catch (ParseException e) {
            }
        }
        throw new ParseException("Unable to parse date: " + dateString, 0);
    }
}
