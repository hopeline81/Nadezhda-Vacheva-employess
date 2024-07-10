package com.example.nadezhdavachevaemployees.model;

import java.util.Objects;

public class EmployeePair {

    private final int firstEmployeeId;
    private final int secondEmployeeId;
    private final int projectId;
    private long totalOverlapDays;

    public EmployeePair(int firstEmployeeId, int secondEmployeeId, int projectId) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.projectId = projectId;
        this.totalOverlapDays = 0;
    }

    public void addOverlapDays(long days) {
        this.totalOverlapDays += days;
    }

    public long getTotalOverlapDays() {
        return totalOverlapDays;
    }

    public int getEmployeeId1() {
        return firstEmployeeId;
    }

    public int getEmployeeId2() {
        return secondEmployeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setTotalOverlapDays(long totalOverlapDays) {
        this.totalOverlapDays = totalOverlapDays;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstEmployeeId, secondEmployeeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeePair that = (EmployeePair) obj;
        return (firstEmployeeId == that.firstEmployeeId && secondEmployeeId == that.secondEmployeeId) ||
                (firstEmployeeId == that.secondEmployeeId && secondEmployeeId == that.firstEmployeeId);
    }
}
