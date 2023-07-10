package project.cnu.daehakro.domain.chat.consumer;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.dto.ChatRequestDto;
import project.cnu.daehakro.domain.chat.service.ChatService;
import project.cnu.daehakro.domain.entity.ChatMessage;

import java.sql.Timestamp;

/**
 * Consumer
 */
@Component
@AllArgsConstructor
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    SimpMessagingTemplate template;

    private final ChatService chatService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
    )
    public void listen(ChatMessageDto message) {
        System.out.println("sending via kafka listener..");

        System.out.println("Received message: " + message.getContent());
        System.out.println("Sender id: " + message.getSender());
        System.out.println("Chat room id: " + message.getRoomId());
        System.out.println("Timestamp: " + message.getCreateAt());
        // listen 되자마자 디비로 바로 insert 박아주기
        // chatService.sendMessage(message);

        template.convertAndSend("/topic/group/", message);
        /**
         * "/topic/group"는 메시지를 보낼 대상 주제(Topic)를 지정하는 부분
         * 이 경우, /topic/group이라는 주제에 메시지를 전송함
         * WebSocket 클라이언트 측에서 해당 주제를 구독하고 있는 경우, 메시지를 수신
         */

    }

}
