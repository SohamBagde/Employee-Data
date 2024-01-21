package com.example.demo.Model;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Writer class
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    private String positionId;
    private String positionStatus;
    private LocalDateTime time;
    private LocalDateTime timeOut;
    private LocalTime timeCardHours;
    private LocalDate payCycleStartDate;
    private LocalDate payCycleEndDate;
    private String employeeName;
    private String fileNumber;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }

    public LocalTime getTimeCardHours() {
        return timeCardHours;
    }

    public void setTimeCardHours(LocalTime timeCardHours) {
        this.timeCardHours = timeCardHours;
    }

    public LocalDate getPayCycleStartDate() {
        return payCycleStartDate;
    }

    public void setPayCycleStartDate(LocalDate payCycleStartDate) {
        this.payCycleStartDate = payCycleStartDate;
    }

    public LocalDate getPayCycleEndDate() {
        return payCycleEndDate;
    }

    public void setPayCycleEndDate(LocalDate payCycleEndDate) {
        this.payCycleEndDate = payCycleEndDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }
}
