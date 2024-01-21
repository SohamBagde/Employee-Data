package com.example.demo.Data;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.example.demo.Model.Employee;

// Class that puts together a batch job
@Configuration
public class EmployeeConfiguration {
    private final String[] FIELD_NAMES = {
            "position_ID",
            "position_Status",
            "time",
            "time_Out",
            "time_Card_Hours",
            "pay_Cycle_Start_Date",
            "pay_Cycle_End_Date",
            "employee_Name",
            "file_Number"
    };

    // Defines a Reader
    @Bean
    public FlatFileItemReader<EmployeeInput> reader() {
        return new FlatFileItemReaderBuilder<EmployeeInput>()
                .name("employeeItemReader")
                .resource(new ClassPathResource("file1.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .targetType(EmployeeInput.class)
                .build();
    }

    // Defines a processor
    @Bean
    public EmployeeItemProcessor processor() {
        return new EmployeeItemProcessor();
    }

    // Defines a Writer
    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .sql("INSERT INTO employee (position_id, position_status, time, time_out, time_card_hours, pay_cycle_start_date, pay_cycle_end_date, employee_name, file_number) VALUES (:positionId, :positionStatus, :time, :timeOut, :timeCardHours, :payCycleStartDate, :payCycleEndDate, :employeeName, :fileNumber)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    // Defines A Job
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    // Defines a Step
    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
            FlatFileItemReader<EmployeeInput> reader, EmployeeItemProcessor processor,
            JdbcBatchItemWriter<Employee> writer) {
        return new StepBuilder("step1", jobRepository)
                .<EmployeeInput, Employee>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
