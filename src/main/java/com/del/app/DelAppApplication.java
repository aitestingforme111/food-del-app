package com.del.app;

import com.del.app.observer.DeliveryTimeCalculator;
import com.del.app.observer.OrderSubject;
import com.del.app.observer.RiderAssignmentObserver;
import com.del.app.service.OrderService;
import com.del.app.service.RiderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DelAppApplication {

    private final OrderSubject orderSubject;
    private final DeliveryTimeCalculator deliveryTimeCalculator;
    private final RiderService riderService;
    private final OrderService orderService;

    public DelAppApplication(OrderSubject orderSubject,
                             DeliveryTimeCalculator deliveryTimeCalculator,
                             RiderService riderService,
                             OrderService orderService) {
        this.orderSubject = orderSubject;
        this.deliveryTimeCalculator = deliveryTimeCalculator;
        this.riderService = riderService;
        this.orderService = orderService;
    }

    @Bean
    public RiderAssignmentObserver riderAssignmentObserver(RiderService riderService, DeliveryTimeCalculator deliveryTimeCalculator) {
        return new RiderAssignmentObserver(riderService, deliveryTimeCalculator, orderService);
    }

    @Bean
    public CommandLineRunner registerObserver() {
        return args -> {
            orderSubject.registerObserver(riderAssignmentObserver(riderService, deliveryTimeCalculator));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DelAppApplication.class, args);
    }
}
