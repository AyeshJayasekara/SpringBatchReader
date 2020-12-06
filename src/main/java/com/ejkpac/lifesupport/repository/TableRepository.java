package com.ejkpac.lifesupport.repository;

import com.ejkpac.lifesupport.model.BillingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@SuppressWarnings("*")
public interface TableRepository extends PagingAndSortingRepository<BillingModel, String> {

    Page<BillingModel> findAllByStatusAndLogTimestampBetween(boolean status, Date start, Date end, Pageable pageable);
    Page<BillingModel> findAllByLogTimestampBetweenAndStatus(Date start, Date end, boolean status, Pageable pageable);

}
