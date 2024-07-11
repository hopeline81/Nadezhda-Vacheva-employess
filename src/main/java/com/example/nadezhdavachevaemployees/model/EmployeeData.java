package com.example.nadezhdavachevaemployees.model;

import java.util.Date;

public class EmployeeData {

    private int employeeId;
    private int projectId;
    private Date dateFrom;
    private Date dateTo;

    public EmployeeData(int employeeId, int projectId, Date dateFrom, Date dateTo) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getProjectId() {
        return projectId;
    }
}
