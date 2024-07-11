package com.example.nadezhdavachevaemployees.service;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.example.nadezhdavachevaemployees.model.EmployeePair;
import java.util.List;

public interface OverlappingCalculation {

    EmployeePair getResult(List<EmployeeData> employeeData);
}
