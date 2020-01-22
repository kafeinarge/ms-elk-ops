package com.turkcell.poc.customerlogger;

import com.turkcell.poc.customerlogger.entity.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com.turkcell.poc.customerlogger.repository")
@SpringBootApplication
public class CustomerLoggerApplication {


    public static void main(String[] args) {
        SpringApplication.run(CustomerLoggerApplication.class, args);
    }


    @Bean(name = "customer")
    public Customer customer(){
        return new Customer();
    }
}
