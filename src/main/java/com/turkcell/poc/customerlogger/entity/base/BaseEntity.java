package com.turkcell.poc.customerlogger.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(of = "id")
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;

    private Date createdAt;

    private Date updatedAt;

}
