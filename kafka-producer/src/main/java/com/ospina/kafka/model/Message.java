package com.ospina.kafka.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@ToString
public class Message {
    private String author;
    private String content;
}
