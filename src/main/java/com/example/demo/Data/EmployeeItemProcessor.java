package com.example.demo.Data;

// Processor Class
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.example.demo.Model.Employee;

public class EmployeeItemProcessor implements ItemProcessor<EmployeeInput, Employee> {

  private static final Logger log = LoggerFactory.getLogger(EmployeeItemProcessor.class);

  @Override
  public Employee process(final EmployeeInput employeeInput) {
    Employee employee = new Employee();

    employee.setPositionId(employeeInput.getPosition_ID());
    employee.setPositionStatus(employeeInput.getPosition_Status());

    try {
      // Parsing the time from the user input using LocalDateTime and a specified
      // format
      LocalDateTime myTime = LocalDateTime.parse(employeeInput.getTime(),
          DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
      employee.setTime(myTime);
    } catch (Exception e) {
      System.out.println("Time Format Parsing Exception" + e.getMessage());
    }

    try {
      // Parsing the time-out from the user input using LocalDateTime and a specified
      // format
      LocalDateTime myTime1 = LocalDateTime.parse(employeeInput.getTime_Out(),
          DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
      employee.setTimeOut(myTime1);
    } catch (Exception e) {
      System.out.println("timeOut Format Parsing Exception" + e.getMessage());
    }

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
      // Parsing the time card hours from the user input using LocalTime and the
      // specified formatter
      LocalTime timeCardHours = LocalTime.parse(employeeInput.getTime_Card_Hours(), formatter);
      employee.setTimeCardHours(timeCardHours);
    } catch (Exception e) {
      System.out.println("timeCardHours Format Parsing Exception " + e.getMessage());
    }

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      // Parsing the pay cycle start date from the user input using LocalDate and the
      // specified formatter
      LocalDate parsedDate2 = LocalDate.parse(employeeInput.getPay_Cycle_Start_Date(), formatter);
      employee.setPayCycleStartDate(parsedDate2);
    } catch (Exception e) {
      System.out.println("payCyclestartData Format Parsing Exception " + e.getMessage());
    }

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      // Parsing the pay cycle end date from the user input using LocalDate and the
      // specified formatter
      LocalDate parsedDate2 = LocalDate.parse(employeeInput.getPay_Cycle_End_Date(), formatter);
      employee.setPayCycleEndDate(parsedDate2);
    } catch (Exception e) {
      System.out.println("payCycleEndData Format Parsing Exception " + e.getMessage());
    }

    employee.setEmployeeName(employeeInput.getEmployee_Name());
    employee.setFileNumber(employeeInput.getFile_Number());

    return employee;
  }

}