package com.ejkpac.lifesupport.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CSVModel {

    private String operatorName;
    private String serviceProvider;
    private String applicationName;
    private String currency;
    private BigDecimal amount;


}
