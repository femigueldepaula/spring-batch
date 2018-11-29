package br.com.felipe.batchdataload.configuration;

import br.com.felipe.batchdataload.mapper.PersonRowMapper;
import br.com.felipe.batchdataload.model.Person;
import br.com.felipe.batchdataload.writer.AmqpPersonItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class BatchConfiguration{

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AmqpPersonItemWriter amqpPersonItemWriter;

    @Autowired
    private PersonRowMapper personRowMapper;

    @Bean
    public DataSource dataSource(){
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/recuperacao?useTimezone=true&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("q1w2e3r4@");

        return dataSource;

    }

    @Bean
    public JdbcCursorItemReader<Person> reader(){
        JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<Person>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM person");
        reader.setRowMapper(personRowMapper);

        return reader;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Person, Person> chunk(10)
                .reader(reader())
                //.processor(processor())
                .writer(amqpPersonItemWriter)
                .build();
    }

    @Bean
    public Job exportUserJob() {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}
