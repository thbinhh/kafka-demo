package com.example.Consumer.service.consumer;

import com.example.Consumer.domain.FoodOrder;
import com.example.Consumer.domain.dto.FoodOrderDTO;
import com.example.Consumer.service.FoodOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class consumer {
    private static final String orderTopic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final FoodOrderService foodOrderService;


    @Autowired
    public consumer(ObjectMapper objectMapper, ModelMapper modelMapper, FoodOrderService foodOrderService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.foodOrderService = foodOrderService;
    }

//    @KafkaListener(topics = orderTopic, groupId = "default")
    @KafkaListener(topicPartitions = { @TopicPartition(topic = orderTopic, partitions = { "0" }) }, groupId = "default")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed 1 {}", message);

        FoodOrderDTO foodOrderDto = objectMapper.readValue(message, FoodOrderDTO.class);
        FoodOrder foodOrder = modelMapper.map(foodOrderDto, FoodOrder.class);

        foodOrderService.persistFoodOrder(foodOrder);
    }
}
