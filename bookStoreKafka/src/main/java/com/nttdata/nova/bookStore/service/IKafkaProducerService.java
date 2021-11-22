package com.nttdata.nova.bookStore.service;

import org.springframework.kafka.core.KafkaTemplate;

public interface IKafkaProducerService {

    public void sendMessage(KafkaTemplate<String, String> kafkaTemplate);
    
}
