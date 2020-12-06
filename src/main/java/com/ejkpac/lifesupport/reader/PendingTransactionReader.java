package com.ejkpac.lifesupport.reader;


import com.ejkpac.lifesupport.model.BillingModel;
import com.ejkpac.lifesupport.repository.TableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.*;

@Configuration
@Slf4j
public class PendingTransactionReader{


    @Value("${status.batch.batchSize}")
    private String batchSize;

    @Value("${status.batch.startOn}")
    private long startMillis;

    @Value("${status.batch.endsOn}")
    private long endMillis;

    @Value("${status.batch.duration}")
    private long durationMillis;

    private int iteration = 0;

    private final TableRepository tableRepository;

    @Autowired
    public PendingTransactionReader(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


    @Bean(name = "pendingPaymentReader")
    @StepScope
    public RepositoryItemReader<BillingModel> reader() throws Exception {
        RepositoryItemReader<BillingModel> reader = new RepositoryItemReader<>();

        reader.setRepository(tableRepository);
        reader.setMethodName("findAllByLogTimestampBetweenAndStatus");
        reader.setSort(Collections.singletonMap("logTimestamp", Sort.Direction.ASC));
        reader.setPageSize(Integer.parseInt(batchSize));

        ArrayList<Object> args = new ArrayList<>();

        Date startTimestamp = startTimestamp();
        args.add(startTimestamp);
        args.add(new Date(startTimestamp.getTime() + durationMillis - 1));
        args.add(false);

        reader.setArguments(args);

        log.info("READING>>>");

        log.info("==================================");
        log.info(" START AT : {}",startTimestamp );
        log.info(" END AT : {}", new Timestamp(startTimestamp.getTime() + durationMillis - 1));
        log.info("==================================");

        iteration++;
        return reader;
    }

    private Timestamp startTimestamp(){
        if(startMillis + ( iteration * durationMillis) > endMillis){
            System.exit(1);
        }

        return new Timestamp(startMillis + ( iteration * durationMillis));
    }


}
