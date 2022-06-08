package com.ospina.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ospina.kafka.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${TOPIC_NAME_PRODUCER}", groupId = "default")
    public void read(String message) throws JsonProcessingException {
        Message myMessage = objectMapper.readValue(message, Message.class);
        log.info("author: " + myMessage.getAuthor());
        log.info("content: " + myMessage.getContent());
    }
}
