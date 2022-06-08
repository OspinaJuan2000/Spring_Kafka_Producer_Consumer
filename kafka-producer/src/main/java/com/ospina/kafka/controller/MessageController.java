package com.ospina.kafka.controller;

import com.ospina.kafka.model.Message;
import com.ospina.kafka.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Value("${TOPIC_NAME_PRODUCER}")
    private String topic;

    @PostMapping(value = "send", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> send(@RequestBody Message message) {
        messageService.send(topic, message);
        return ResponseEntity.ok("Message sent to kafka topic");
    }
}
