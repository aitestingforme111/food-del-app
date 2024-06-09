package com.del.app.dto;

import com.del.app.model.OrderItem;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class CreateOrderRequest {
    private Long userId;
    private String restaurantName;
    private List<OrderItem> orderItems;
}
