package com.ospina.kafka.service;

import com.ospina.kafka.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {
    @Autowired
    KafkaTemplate<String, Message> kafkaTemplate;

    public void send(String topic, Message message) {
        log.info("Payload: " + message);
        log.info("Topic Name: " + topic);
        kafkaTemplate.send(topic, message);
    }
}
