package br.com.felipe.batchdataload.mapper;

import br.com.felipe.batchdataload.model.Address;
import br.com.felipe.batchdataload.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonRowMapper implements RowMapper<Person> {

    Logger logger = LoggerFactory.getLogger(PersonRowMapper.class);

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        Address address = Address.builder()
                .streetName(rs.getString("street_name"))
                .streetNumber(rs.getString("street_number"))
                .streetComplement(rs.getString("street_complement"))
                .county(rs.getString("county"))
                .zipCode(rs.getString("zip_code"))
                .city(rs.getString("city"))
                .state(rs.getString("state"))
                .build();

        Person person = Person.builder()
                .id(rs.getString("id"))
                .name(rs.getString("name"))
                .address(address)
                .build();

        return person;
    }

}
