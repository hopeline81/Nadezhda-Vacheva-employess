package com.example.nadezhdavachevaemployees.controller;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.example.nadezhdavachevaemployees.model.EmployeePair;
import com.example.nadezhdavachevaemployees.service.OverlappingCalculation;
import com.example.nadezhdavachevaemployees.service.OverlappingCalculationImpl;
import com.example.nadezhdavachevaemployees.util.CSVParser;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EmployeeStatisticController {

    private final OverlappingCalculation overlappingCalculation;

    @Autowired
    public EmployeeStatisticController(OverlappingCalculation overlappingCalculation) {
        this.overlappingCalculation = overlappingCalculation;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam("file") MultipartFile file, Model model) {
        List<EmployeeData> employeeData = CSVParser.parseCSV(file);

        EmployeePair employeesMaxPair = overlappingCalculation.getResult(employeeData);

        if (employeesMaxPair != null) {
            model.addAttribute("employeeId1", employeesMaxPair.getFirstEmployeeId());
            model.addAttribute("employeeId2", employeesMaxPair.getSecondEmployeeId());
            model.addAttribute("projectId", employeesMaxPair.getProjectId());
            model.addAttribute("totalOverlapDays", employeesMaxPair.getTotalOverlapDays());
        } else {
            model.addAttribute("message", "No overlapping projects found.");
        }

        return "result";
    }
}
