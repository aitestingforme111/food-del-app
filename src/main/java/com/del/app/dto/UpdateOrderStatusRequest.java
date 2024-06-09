package com.del.app.dto;

import com.del.app.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateOrderStatusRequest {
    private OrderStatus newStatus;
}
