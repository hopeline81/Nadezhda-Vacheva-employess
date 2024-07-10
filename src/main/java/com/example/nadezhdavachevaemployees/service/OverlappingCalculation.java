package com.example.nadezhdavachevaemployees.service;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.example.nadezhdavachevaemployees.model.EmployeePair;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OverlappingCalculation {

    public Map<EmployeePair, Long> calculateOverlaps(List<EmployeeData> projects) {
        Map<EmployeePair, Long> overlapMap = new HashMap<>();

        for (int i = 0; i < projects.size(); i++) {
            for (int j = i + 1; j < projects.size(); j++) {
                EmployeeData firstEmployee = projects.get(i);
                EmployeeData secondEmployee = projects.get(j);

                if (firstEmployee.getProjectId() == secondEmployee.getProjectId() &&
                        firstEmployee.getEmployeeId() != secondEmployee.getEmployeeId()) {
                    Date maxStart = firstEmployee.getDateFrom().after(secondEmployee.getDateFrom())
                            ? firstEmployee.getDateFrom() : secondEmployee.getDateFrom();
                    Date minEnd = firstEmployee.getDateTo().before(secondEmployee.getDateTo())
                            ? firstEmployee.getDateTo() : secondEmployee.getDateTo();

                    if (maxStart.before(minEnd)) {
                        long overlapDays =
                                (minEnd.getTime() - maxStart.getTime()) / (1000 * 60 * 60 * 24);
                        EmployeePair pair = new EmployeePair(firstEmployee.getEmployeeId(),
                                secondEmployee.getEmployeeId(), firstEmployee.getProjectId());

                        overlapMap.put(pair, overlapMap.getOrDefault(pair, 0L) + overlapDays);
                    }
                }
            }
        }

        return overlapMap;
    }
}
