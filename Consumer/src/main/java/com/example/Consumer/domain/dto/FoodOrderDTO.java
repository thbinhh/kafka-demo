package com.example.Consumer.domain.dto;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class FoodOrderDTO {
    String item;
    Double amount;
}
