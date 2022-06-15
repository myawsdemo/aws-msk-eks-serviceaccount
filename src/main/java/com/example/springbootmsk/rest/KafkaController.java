package com.example.springbootmsk.rest;

import com.example.springbootmsk.service.GetSecretValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate producerFactory;

    @Autowired
    private GetSecretValue getSecretValue;


    @GetMapping("produce")
    String produceMessage() {
        producerFactory.send("ExampleTopicName", "cheers");
        return "";
    }

    @GetMapping("secret")
    String produceMessage(@RequestParam String secret) {
        return getSecretValue.getSecret(secret);
    }

}
