package com.ejkpac.lifesupport.config;


import com.ejkpac.lifesupport.model.BillingModel;
import com.ejkpac.lifesupport.model.CSVModel;
import com.ejkpac.lifesupport.processor.BillingProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class BillingBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final RepositoryItemReader<BillingModel> reader;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemWriter<CSVModel> csvWriter;
    private final BillingProcessor billingProcessor;
    private final JobLauncher jobLauncher;

    @Value("${status.batch.batchSize}")
    private String batchSize;

    @Autowired
    public BillingBatchConfiguration(
            JobBuilderFactory jobBuilderFactory ,RepositoryItemReader<BillingModel> itemReader, StepBuilderFactory stepBuilderFactory,
            ItemWriter<CSVModel> csvWriter,
            BillingProcessor billingProcessor, JobLauncher jobLauncher) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.reader = itemReader;
        this.stepBuilderFactory = stepBuilderFactory;
        this.csvWriter = csvWriter;
        this.billingProcessor = billingProcessor;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(initialDelayString = "${status.batch.initialDelay}", fixedDelayString = "${status.batch.interval}")
    public void perform() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException {

        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(pendingTransactionFinalizerJob(), params);

    }


    @Bean
    public Job pendingTransactionFinalizerJob() {
        return this.jobBuilderFactory.get("pendingTransactionFinalizerJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<BillingModel, CSVModel>chunk(Integer.parseInt(batchSize))
                .reader(reader)
                .processor(billingProcessor)
                .writer(csvWriter)
                .build();
    }







//    class ModelMapper implements RowMapper<BillingModel> {
//
//        @Override
//        public BillingModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//
//            String operatorName= rs.getString("operator_name");
//            String serviceProvider= rs.getString("service_provider");
//            String applicationName= rs.getString("application_name");
//            String currency= rs.getString("currency");
//            BigDecimal amount= rs.getBigDecimal("amount");
//
//
//            BillingModel model = new BillingModel();
//
//            model.setAmount(amount);
//            model.setCurrency(currency);
//            model.setApplicationName(applicationName);
//            model.setServiceProvider(serviceProvider);
//            model.setOperatorName(operatorName);
//
//            return model;
//}

}



