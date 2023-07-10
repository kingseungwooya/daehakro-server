package project.cnu.daehakro.domain.chat.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.topic.name}")
    private String topicName;

    // @Value("${spring.kafka.topic-json.name}")
    // private String topicJsonName;

    @Bean
    public NewTopic daehakRoTopic(){
        return TopicBuilder.name(topicName)
                .partitions(5)
                .build();
    }

    // @Bean
    // public NewTopic daehakRoJsonTopic(){
    //     return TopicBuilder.name(topicJsonName)
    //             .build();
    // }
}
