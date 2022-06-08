package com.ospina.kafka.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Message {
    private String author;
    private String content;
}
