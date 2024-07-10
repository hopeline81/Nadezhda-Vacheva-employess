package com.example.nadezhdavachevaemployees.controller;

import com.example.nadezhdavachevaemployees.model.EmployeeData;
import com.example.nadezhdavachevaemployees.model.EmployeePair;
import com.example.nadezhdavachevaemployees.service.OverlappingCalculation;
import com.example.nadezhdavachevaemployees.util.CSVParser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EmployeeStatisticController {

    private final OverlappingCalculation overlappingCalculation;

    public EmployeeStatisticController(OverlappingCalculation overlappingCalculation) {
        this.overlappingCalculation = overlappingCalculation;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam("file") MultipartFile file, Model model) {
        List<EmployeeData> projects = CSVParser.parseCSV(file);
        Map<EmployeePair, Long> overlapMap = overlappingCalculation.calculateOverlaps(projects);

        EmployeePair maxPair = null;
        long maxDays = 0;

        for (Map.Entry<EmployeePair, Long> entry : overlapMap.entrySet()) {
            if (entry.getValue() > maxDays) {
                maxDays = entry.getValue();
                maxPair = entry.getKey();
            }
        }

        if (maxPair != null) {
            model.addAttribute("employeeId1", maxPair.getEmployeeId1());
            model.addAttribute("employeeId2", maxPair.getEmployeeId2());
            model.addAttribute("projectId", maxPair.getProjectId());
            model.addAttribute("totalOverlapDays", maxDays);
        } else {
            model.addAttribute("message", "No overlapping projects found.");
        }

        return "result";
    }
}
