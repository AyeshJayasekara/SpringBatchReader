package com.ejkpac.lifesupport.writer;


import com.ejkpac.lifesupport.model.CSVModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


import java.util.List;

@Configuration
@Slf4j
public class CSVWriter implements ItemWriter<CSVModel> {



    private final FlatFileItemWriter<CSVModel> writer;
    private ExecutionContext jobContext;

    @Autowired
    public CSVWriter(@Value("${status.batch.outputPath}") String folderPath) throws Exception {

        writer = new FlatFileItemWriter<>();
        Resource outputResource = new FileSystemResource(folderPath);
        writer.setAppendAllowed(true);
        writer.setResource(outputResource);


        String exportFileHeader = "operator_name;service_provider;application_name;currency;amount ";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        writer.setHeaderCallback(headerWriter);


        DelimitedLineAggregator<CSVModel> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(";");

        BeanWrapperFieldExtractor<CSVModel> fieldExtractor = new BeanWrapperFieldExtractor<>();
        String[] fieldNames = {"operatorName", "serviceProvider", "applicationName", "currency", "amount"};


        fieldExtractor.setNames(fieldNames);
        aggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(aggregator);
        writer.afterPropertiesSet();

    }

    @Override
    public void write(List<? extends CSVModel> list) throws Exception {
        writer.open(jobContext);
        log.info("WRITING>>>");
        writer.write(list);
    }

    @BeforeStep
    public void retrieveInterStepData(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        this.jobContext = jobExecution.getExecutionContext();


    }
}
