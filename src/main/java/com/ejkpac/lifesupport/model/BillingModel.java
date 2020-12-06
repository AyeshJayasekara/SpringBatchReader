package com.ejkpac.lifesupport.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "billing")
@Data
public class BillingModel {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "bizao_alias")
    private String bizaoAlias;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "on_behalf_of")
    private String onBehalfOf;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "operator_ref")
    private String operatorReference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private boolean status;

    @Column(name = "log_timestamp")
    private Date logTimestamp;

    @Column(name = "created_timestamp")
    private Date createdTimestamp;
}
