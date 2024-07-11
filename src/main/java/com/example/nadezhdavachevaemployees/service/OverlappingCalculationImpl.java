package com.example.nadezhdavachevaemployees.service;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.example.nadezhdavachevaemployees.model.EmployeePair;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OverlappingCalculationImpl implements OverlappingCalculation {

    @Override
    public EmployeePair getResult(List<EmployeeData> employeeData) {
        Map<EmployeePair, Integer> overlaps = calculateOverlaps(employeeData);
        EmployeePair employeesMaxPair = new EmployeePair();
        int maxDays = 0;

        for (Map.Entry<EmployeePair, Integer> entry : overlaps.entrySet()) {
            if (entry.getValue() > maxDays) {
                maxDays = entry.getValue();
                employeesMaxPair = entry.getKey();
            }
        }
        employeesMaxPair.setTotalOverlapDays(maxDays);

        return employeesMaxPair;
    }

    private Map<EmployeePair, Integer> calculateOverlaps(List<EmployeeData> data) {
        Map<EmployeePair, Integer> overlaps = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                EmployeeData firstEmployee = data.get(i);
                EmployeeData secondEmployee = data.get(j);

                if (firstEmployee.getProjectId() == secondEmployee.getProjectId() &&
                        firstEmployee.getEmployeeId() != secondEmployee.getEmployeeId()) {

                    Date maxStart = firstEmployee.getDateFrom().after(secondEmployee.getDateFrom())
                            ? firstEmployee.getDateFrom() : secondEmployee.getDateFrom();
                    Date minEnd = firstEmployee.getDateTo().before(secondEmployee.getDateTo())
                            ? firstEmployee.getDateTo() : secondEmployee.getDateTo();

                    if (maxStart.before(minEnd)) {
                        int overlapDays =
                                (int) ((minEnd.getTime() - maxStart.getTime()) / (1000 * 60 * 60 * 24));
                        EmployeePair pair = new EmployeePair(firstEmployee.getEmployeeId(),
                                secondEmployee.getEmployeeId(), firstEmployee.getProjectId());

                        overlaps.put(pair, overlaps.getOrDefault(pair, 0) + overlapDays);
                    }
                }
            }
        }

        return overlaps;
    }
}
