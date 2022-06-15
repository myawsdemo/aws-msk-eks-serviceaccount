package com.example.springbootmsk.configuration;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.security.scram.ScramLoginModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    public static final String SASL_SSL = "SASL_SSL";
    public static final String SCRAM_SHA_256 = "SCRAM-SHA-512";
    public static final String TMP_KAFKA_CLIENT_TRUSTSTORE_JKS = "/tmp/kafka.client.truststore.jks";
    private final String bootstrapServers = "b-2-public.msktutorialcluster.x6juht.c24.kafka.us-east-1.amazonaws.com:9196,b-3-public.msktutorialcluster.x6juht.c24.kafka.us-east-1.amazonaws.com:9196,b-1-public.msktutorialcluster.x6juht.c24.kafka.us-east-1.amazonaws.com:9196";

    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SASL_SSL);
        props.put(SaslConfigs.SASL_MECHANISM, SCRAM_SHA_256);
        props.put("ssl.truststore.location", TMP_KAFKA_CLIENT_TRUSTSTORE_JKS);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                "%s required username=\"%s\" " + "password=\"%s\";", ScramLoginModule.class.getName(), "shenhua", "shenhua"
        ));

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SASL_SSL);
        props.put(SaslConfigs.SASL_MECHANISM, SCRAM_SHA_256);
        props.put("ssl.truststore.location", TMP_KAFKA_CLIENT_TRUSTSTORE_JKS);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                "%s required username=\"%s\" " + "password=\"%s\";", ScramLoginModule.class.getName(), "shenhua", "shenhua"
        ));

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        configs.put("security.protocol", SASL_SSL);
        configs.put("sasl.mechanism", SCRAM_SHA_256);
        configs.put("ssl.truststore.location", TMP_KAFKA_CLIENT_TRUSTSTORE_JKS);
        configs.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required " +
                "username=shenhua" +
                "password=shenhua;");

        return new KafkaAdmin(configs);
    }


}
