package com.turkcell.poc.customerlogger.service.impl;

import com.google.gson.Gson;
import com.turkcell.poc.customerlogger.entity.Customer;
import com.turkcell.poc.customerlogger.repository.CustomerRepository;
import com.turkcell.poc.customerlogger.service.QueueIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class QueueIOImpl implements QueueIO {

    @Value("${kafka-client-id}")
    private String kafkaClientId;

    @Value("${kafka-bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka-consumer-key-deserializer}")
    private String kafkaProducerKeyDeserializer;

    @Value("${kafka-consumer-value-deserializer}")
    private String kafkaProducerValueDeserializer;

    @Value("${kafka-order-topic}")
    private String kafkaOrderTopic;

    @Value("${kafka-order-group-id}")
    private String kafkaOrderGroupId;

    @Value("${auto_commit_interval_ms_config}")
    private String autoCommitIntervalMsConfig;

    @Value("${session_timeout_ms_config}")
    private String sessionTimeoutMsConfig;

    final
    CustomerRepository customerRepository;

    final
    Client client;

    @Autowired
    Customer customerBean;

    public QueueIOImpl(CustomerRepository customerRepository, Client client) {
        this.customerRepository = customerRepository;
        this.client = client;
    }


    /**
     * getting orders from queue and store to document db
     *
     */
    @Override
    @PostConstruct
    public void storeToES() throws InterruptedException {
        KafkaConsumer<String, String> consumer = createConsumer();

        consumer.subscribe(Collections.singletonList(kafkaOrderTopic));

        Gson gson = new Gson();
        List<Customer> customersFromQueue = new ArrayList<>();

        while (true) {
            customersFromQueue.clear();
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            log.info(records.count() + " customers has been audited");
            for (ConsumerRecord<String, String> record : records) {
                log.debug("Consumer Record: topic:" + record.topic() + " offset:" + record.offset());
                Customer customer = gson.fromJson(record.value(), Customer.class);
                customerBean.setTckn(customer.getTckn());
                customersFromQueue.add(customer);
            }
            if(customersFromQueue.size()>0)
                customerRepository.saveAll(customersFromQueue);
        }
    }


    /**
     * returns consumer of kafka
     *
     * @return
     */
    public KafkaConsumer<String, String> createConsumer() {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaBootstrapServers);
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,kafkaProducerKeyDeserializer);
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,kafkaProducerValueDeserializer);
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaOrderGroupId);
        configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        configProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMsConfig);
        configProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMsConfig);

        return new KafkaConsumer<>(configProperties);
    }





}
