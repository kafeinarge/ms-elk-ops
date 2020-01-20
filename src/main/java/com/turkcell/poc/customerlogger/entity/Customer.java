package com.turkcell.poc.customerlogger.entity;

import com.turkcell.poc.customerlogger.entity.base.BaseEntity;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "tckn")
@Data
@Document(indexName = "customers")
public class Customer extends BaseEntity {

    private String tckn;

    private String name;

    private String surname;


}
