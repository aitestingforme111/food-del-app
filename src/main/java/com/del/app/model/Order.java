package com.del.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date orderTime;
    private Long estimatedDeliveryTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    private User user;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToOne
    private Rider rider;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}