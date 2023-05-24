package com.interpackage.resources.producers;

import com.interpackage.basedomains.dto.RouteEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class RouteProducer {

    private final NewTopic topic;
    private final KafkaTemplate<String, RouteEvent> kafkaTemplate;

    public RouteProducer(NewTopic topic, KafkaTemplate<String, RouteEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RouteEvent event){
        //Message
        Message<RouteEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
