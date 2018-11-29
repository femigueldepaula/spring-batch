package br.com.felipe.batchdataload.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Address {

    private String streetName;
    private String streetNumber;
    private String streetComplement;
    private String county;
    private String zipCode;
    private String city;
    private String state;
}
