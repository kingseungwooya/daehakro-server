package project.cnu.daehakro.domain.chat.controller;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.dto.ChatRoomDto;
import project.cnu.daehakro.domain.chat.producer.KafkaProducer;
import project.cnu.daehakro.domain.chat.service.ChatService;
import project.cnu.daehakro.domain.common.ResponseDto;
import project.cnu.daehakro.domain.enums.ResponseEnum;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/chat")
public class ChatController {

    private final KafkaProducer kafkaProducer;

    private final ChatService chatService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> roomDetail(String memberId, @PathVariable @NotNull Long roomId) {
        ChatRoomDto roomDto = chatService.roomDetail(memberId, roomId);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.CHAT_ROOM_DETAIL_SUCCESS, roomDto), HttpStatus.OK);
    }

    @PostMapping(value = "/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody ChatMessageDto message) {
        message.setCreateAt(new Timestamp(System.currentTimeMillis()));
        //Sending the message to kafka topic queue
        kafkaProducer.sendMessage(message);
    }

    //    -------------- WebSocket API ----------------
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public ChatMessageDto broadcastGroupMessage(@Payload ChatMessageDto message) {
        // Sending this message to all the subscribers
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