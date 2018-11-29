package br.com.felipe.batchdataload.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {
    private String id;
    private String name;
    private Address address;
}
