# Producir y consumir mensajes de Kafka con Spring Boot & Spring Cloud

## Apache Kafka

Es un sistema distribuido de transmisión de mensajes. Plataforma de alto rendimiento y baja latencia para manejar datos en tiempo real.

### Características

- Altamente escalable
- Posee tolerancia a fallos
- Maneja datos en tiempo real
- Existen productores y consumidores
- Posee 2 modelos: **Publicador/Suscriptor** y **Cola de mensajes**

### Conceptos básicos

- **Mensaje:** Datos que se envían para ser almacenados.
- **Tópico:** Categoría donde se publican los mensajes.
- **Productor:** Encargado de publicar los mensajes en los tópicos.
- **Consumidor:** Encargado de obtener los mensajes publicados y procesarlos.

<hr>

**Nota: Apache Kafka está diseñado para trabajar con grandes cantidades de datos y en tiempo real, por eso es una buena opción.**

<hr>

## Spring Boot & Maven

Dependencias utilizadas en el proyecto:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.6.7</version>
</dependency>
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka-test</artifactId>
    <scope>test</scope>
</dependency>

Opcionales:

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.4</version>
</dependency>

```

<hr>

## Configuración productor

```
@Configuration
public class KafkaConfig {
    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<String, Message>(producerFactory());
    }
}
```

## Enviar mensajes desde el productor

```
@Autowired
KafkaTemplate<String, Message> kafkaTemplate;

public void send(String topic, Message message) {
    log.info("Payload: " + message);
    log.info("Topic Name: " + topic);
    kafkaTemplate.send(topic, message);
}
```

## Configuración consumidor

```
@Configuration
public class KafkaConfig {
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Creating a Map of string-object pairs
        Map<String, Object> config = new HashMap<>();

        // Adding the Configuration
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```

## Recibir mensajes desde el consumidor

En el parámetro `topics` pasamos el topic del cual queremos leer mensajes y en el parámetro `groupId` indicamos a cuál grupo está asociado el consumidor.

```
@Autowired
private ObjectMapper objectMapper;

@KafkaListener(topics = "${TOPIC_NAME_PRODUCER}", groupId = "default")
public void read(String message) throws JsonProcessingException {
    Message myMessage = objectMapper.readValue(message, Message.class);
    log.info("author: " + myMessage.getAuthor());
    log.info("content: " + myMessage.getContent());
}
```

<hr>

## A la hora de correr el proyecto

- Tener docker instalado y ejecutar el comando `docker-compose up -d` para montar el Apache Kafka.
- Levantar el proyecto kafka-consumer y kafka-producer.
- Para probar el servicio de productor, acceder a `http://localhost:8000/swagger-ui/index.html`y consumir el único servicio allí disponible o hacerlo desde [postman](https://documenter.getpostman.com/view/11875051/Uz5KnaYa)
- Revisar los mensajes en la consola del proyecto del consumidor.

