package com.sorceshare.aifeedservice.config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.internals.ConsumerFactory;
import reactor.kafka.receiver.internals.DefaultKafkaReceiver;

import java.util.*;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Autowired
    KafkaProperties kafkaProperties;


//    @Bean
//    public ConsumerFactory<String, Article> userConsumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",",kafkaProperties.getBrokers()));
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
//                new JsonDeserializer<>(Article.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Article> userKafkaListenerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Article> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(userConsumerFactory());
//        return factory;
//    }

    @Bean
    public KafkaReceiver kafkaReceiver() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",",kafkaProperties.getBrokers()));
        configProps.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
//        configProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "Kafka-consumer");
        configProps.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        return new DefaultKafkaReceiver(ConsumerFactory.INSTANCE,
                ReceiverOptions.create(configProps).subscription(Collections.singleton(kafkaProperties.getTopic()))
        );
    }
}
