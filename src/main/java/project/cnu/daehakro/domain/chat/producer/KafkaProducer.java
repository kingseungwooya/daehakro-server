package project.cnu.daehakro.domain.chat.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.repository.ChatRoomRepository;
import project.cnu.daehakro.domain.entity.ChatMessage;
import project.cnu.daehakro.domain.entity.ChatRoom;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

@Service
public class KafkaProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    // spring 이 yml 설정에 의해 자동으로 DI 해준다
    private final KafkaTemplate<String, ChatMessageDto> kafkaTemplate;

    private final ChatRoomRepository chatRoomRepository;

    public KafkaProducer(KafkaTemplate<String, ChatMessageDto> kafkaTemplate, ChatRoomRepository chatRoomRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.chatRoomRepository = chatRoomRepository;
    }

    public void sendMessage(ChatMessageDto message) {
        LOGGER.info(String.format("Message sent %s", message.getMemberId()));
        //   kafkaTemplate.send(topicName, message);
        // ListenableFuture<SendResult<String, ChatMessageDto>> future = kafkaTemplate.send(topicName, message);// 메시지를 kafka broker 에게 전송한다 . step1
        // 성공 유무 확인을 위한 콜백 메소드 구현
        ListenableFuture<SendResult<String, ChatMessageDto>> future = kafkaTemplate.send(
                new ProducerRecord<String, ChatMessageDto>(topicName, Long.toString(message.getRoomId()), message)  // custom partitioner 이용을 위한..
        );
// 메시지를 kafka broker 에게 전송한다 . step1

        future.addCallback(success -> {
            LOGGER.info("[성공이닷] offset: {}, partition: {}",
                    success.getRecordMetadata().offset(), success.getRecordMetadata().partition());

        }, fail -> {
            LOGGER.error("[실패닷] error message: {} ", fail.getMessage());
            throw new CustomApiException(ResponseEnum.KAFKA_CONSUME_ERR);
        });

    }
}