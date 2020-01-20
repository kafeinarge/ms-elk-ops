//package com.turkcell.poc.customerlogger.config;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.turkcell.poc.customerlogger.repository")
//public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
//
//    @Override
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("207.154.232.181:32606")
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//}
//
