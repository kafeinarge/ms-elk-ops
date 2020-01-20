package com.turkcell.poc.customerlogger.repository;

import com.turkcell.poc.customerlogger.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends ElasticsearchRepository<Customer, String > {

}
