package com.example.nadezhdavachevaemployees.model;

public class EmployeePair {

    private int firstEmployeeId;
    private int secondEmployeeId;
    private int projectId;
    private int totalOverlapDays;

    public EmployeePair() {
    }

    public EmployeePair(int firstEmployeeId, int secondEmployeeId, int projectId) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.projectId = projectId;
    }

    public int getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public int getTotalOverlapDays() {
        return totalOverlapDays;
    }

    public void setTotalOverlapDays(int totalOverlapDays) {
        this.totalOverlapDays = totalOverlapDays;
    }
}
