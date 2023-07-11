package project.cnu.daehakro.domain.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*
         * setApplicationDestinationPrefixes
         * 도착 경로에 대한 prefix를 설정
         * /app : /topic/chat 이라는 구독을 신청했을 때 실제 경로는 /app/topic/chat
         */
        config.setApplicationDestinationPrefixes("/daehakro");
        /*
         * enableSimpleBroker
         * 메시지 브로커 등록
         * 네이밍 : 보통 broadcast는 /topic, 특정 유저에게 보내는 것은 /queue
         * ver1에선 Queue 다중 채팅은 topic 으로 바꿔야한다.
         */
        config.enableSimpleBroker("/topic/");
        /*
         * setUserDestinationPrefix
         * 특정 유저에게 보낼 때(convertAndSendToUser) prefix
         * default : /user
         */
        // config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * socket 연결 엔드포인트
         */
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}