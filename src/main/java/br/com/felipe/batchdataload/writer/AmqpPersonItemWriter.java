package br.com.felipe.batchdataload.writer;

import br.com.felipe.batchdataload.configuration.BatchConfiguration;
import br.com.felipe.batchdataload.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AmqpPersonItemWriter implements ItemWriter<Person> {

    @Autowired
    RabbitTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(AmqpPersonItemWriter.class);

    @Override
    public void write(List<? extends Person> list) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        for (Person person : list) {
            logger.info(mapper.writeValueAsString(person));
            rabbitTemplate.convertAndSend("person_queue", mapper.writeValueAsString(person));
        }
    }
}
