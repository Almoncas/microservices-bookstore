package com.nttdata.nova.bookStore.entities.KafkaEntities;

import java.util.Calendar;

import com.nttdata.nova.bookStore.dto.BookRegistryDto;
import com.nttdata.nova.bookStore.service.IBookRegistryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    IBookRegistryService bookRegistryService;

    @KafkaListener(topics = "registry" , groupId = "registryGroup")
    public void consume(String  message) {
        logger.info("Consuming Message {}", message);
        bookRegistryService.save(new BookRegistryDto(message,Calendar.getInstance().getTime()));
    }

}