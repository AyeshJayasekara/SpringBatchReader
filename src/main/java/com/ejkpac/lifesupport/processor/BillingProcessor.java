package com.ejkpac.lifesupport.processor;


import com.ejkpac.lifesupport.model.BillingModel;
import com.ejkpac.lifesupport.model.CSVModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BillingProcessor implements ItemProcessor<BillingModel, CSVModel> {


    @Override
    public CSVModel process(BillingModel billingModel){

        log.info("PROCESSING>>>");

        CSVModel csvModel = new CSVModel();
        csvModel.setAmount(billingModel.getAmount());
        csvModel.setCurrency(billingModel.getCurrency());
        csvModel.setServiceProvider(billingModel.getServiceProvider());
        csvModel.setOperatorName(billingModel.getOperatorName());
        csvModel.setApplicationName(billingModel.getApplicationName());
        return csvModel;
    }

}
