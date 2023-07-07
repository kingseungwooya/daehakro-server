package project.cnu.daehakro.domain.chat.consumer;


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

@Component
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    SimpMessagingTemplate template;

   // @Autowired
   // private ChatService websocketService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "chat-group-${T(java.util.UUID).randomUUID()}" // 여러개의 채팅방을 고려해 랜덤으로 생성한다.
            // 문제점 메시지가 들어오때마다 계속 랜덤 생성,,, 무슨 방법 없을까..
    )
    public void listen(ChatMessageDto message,
                       @Header(KafkaHeaders.GROUP_ID) String groupId) {
        System.out.println("sending via kafka listener..");
        System.out.println("Received message: " + message.getContent());
        System.out.println("Sender id: " + message.getSender());
        System.out.println("Chat room id: " + message.getRoomId());
        System.out.println("Timestamp: " + message.getCreateAt());
        // 여기서 DB로 저장 구현해야함 listen 되자마자 디비로 바로 insert 박아주기
        // .. db.save
        template.convertAndSend("/topic/group" + groupId, message);
        /**
         * "/topic/group"는 메시지를 보낼 대상 주제(Topic)를 지정하는 부분
         * 이 경우, /topic/group이라는 주제에 메시지를 전송함
         * WebSocket 클라이언트 측에서 해당 주제를 구독하고 있는 경우, 메시지를 수신
         */

        /**
         * 위의 코드에서 listen 메소드에 @Header(KafkaHeaders.GROUP_ID) String groupId 매개변수를 추가하여
         * Kafka 메시지의 GROUP_ID 헤더 값을 받아옵니다. 그리고 template.convertAndSend("/topic/group-" + groupId, message);
         * 코드에서 해당 groupId를 WebSocket 주제에 동적으로 추가하여 메시지를 전송
         */
    }

}
