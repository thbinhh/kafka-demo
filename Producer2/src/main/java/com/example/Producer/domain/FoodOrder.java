package com.example.Producer.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrder {
    String item;
    Double amount;
}
