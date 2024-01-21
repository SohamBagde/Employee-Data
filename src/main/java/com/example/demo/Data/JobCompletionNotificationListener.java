package com.example.demo.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

// Class that notifies when the job is complete
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

        private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

        private final JdbcTemplate jdbcTemplate;

        public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                        log.info("!!! JOB FINISHED! Time to verify the results");

                        System.out.println("Employee who has worked for more than 14 hours in a single shift: ");
                        jdbcTemplate
                                        .query("SELECT DISTINCT employee_name, position_id FROM employee WHERE DATEDIFF('HOUR', time, time_out) > 14 ORDER BY employee_name",
                                                        (rs, row) -> "Employee name  " + rs.getString(1)
                                                                        + " Position id " + rs.getString(2))
                                        .forEach(str -> System.out.println(str));

                        System.out.println("\n" +"Employee who has worked for 7 consecutive days: ");
                        jdbcTemplate.query(
                                        "SELECT DISTINCT a.employee_name, a.position_id " +
                                                        "FROM employee a, employee b " +
                                                        "WHERE b.employee_name = a.employee_name AND b.position_id = a.position_id AND "
                                                        +
                                                        "b.time > a.time_out AND DATEDIFF('DAY', a.time_out, b.time) = 1 AND NOT EXISTS "
                                                        +
                                                        "(SELECT 1 FROM employee c WHERE c.employee_name = a.employee_name AND c.position_id = a.position_id AND "
                                                        +
                                                        "DATEDIFF('DAY', a.time_out, c.time) > 1 AND DATEDIFF('DAY', a.time_out, c.time) <= 7) "
                                                        +
                                                        "ORDER BY a.employee_name, a.position_id",
                                        (rs, row) -> "Employee Name: " + rs.getString("employee_name")
                                                        + ", Position ID: "
                                                        + rs.getString("position_id"))
                                        .forEach(str -> System.out.println(str));

                        System.out.println("\n" +"Employee who have less than 10 hours of time between shifts but greater than 1 hour: ");
                        jdbcTemplate.query(
                                        "SELECT DISTINCT a.employee_name, a.position_id " +
                                                        "FROM employee a, employee b " +
                                                        "WHERE b.employee_name = a.employee_name AND b.position_id = a.position_id AND "
                                                        +
                                                        "b.time > a.time_out AND (b.time - a.time_out) BETWEEN 1 AND 10 "
                                                        +
                                                        "ORDER BY a.employee_name, a.position_id",
                                        (rs, row) -> "Employee Name: " + rs.getString("employee_name")
                                                        + ", Position ID: "
                                                        + rs.getString("position_id"))
                                        .forEach(str -> System.out.println(str));

                }
        }
}