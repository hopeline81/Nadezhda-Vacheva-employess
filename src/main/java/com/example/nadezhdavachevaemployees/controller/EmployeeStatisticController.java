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

        EmployeePair maxPair = overlappingCalculation.getResult(employeeData);

        if (maxPair != null) {
            model.addAttribute("employeeId1", maxPair.getFirstEmployeeId());
            model.addAttribute("employeeId2", maxPair.getSecondEmployeeId());
            model.addAttribute("projectId", maxPair.getProjectId());
            model.addAttribute("totalOverlapDays", maxPair.getTotalOverlapDays());
        } else {
            model.addAttribute("message", "No overlapping projects found.");
        }

        return "result";
    }
}
