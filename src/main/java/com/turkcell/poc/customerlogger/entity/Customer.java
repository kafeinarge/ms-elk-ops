package com.turkcell.poc.customerlogger.entity;

import com.turkcell.poc.customerlogger.entity.base.BaseEntity;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "tckn")
@Data
@Document(indexName = "customers2_#{customer.getTckn()}")
public class Customer extends BaseEntity {

    private String tckn;

    private String name;

    private String surname;


}
