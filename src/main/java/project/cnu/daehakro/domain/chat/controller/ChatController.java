package project.cnu.daehakro.domain.chat.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.producer.KafkaProducer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
public class ChatController {

    @Autowired
    private KafkaTemplate<String, ChatMessageDto> kafkaTemplate;
    private final KafkaProducer kafkaProducer;

    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody ChatMessageDto message) {
        message.setCreateAt(Timestamp.valueOf(LocalDateTime.now().toString()));
        //Sending the message to kafka topic queue
        kafkaProducer.sendMessage(message);
    }

    //    -------------- WebSocket API ----------------
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public ChatMessageDto broadcastGroupMessage(@Payload ChatMessageDto message) {
        //Sending this message to all the subscribers
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public ChatMessageDto addUser(@Payload ChatMessageDto message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

}