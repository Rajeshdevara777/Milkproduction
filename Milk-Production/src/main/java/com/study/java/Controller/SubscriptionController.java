package com.study.java.Controller;


import com.study.java.DTO.SubscribeRequest;
import com.study.java.Entity.MilkOrder;
import com.study.java.Repository.MilkOrderRepository;
import com.study.java.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // keep open for dev; restrict later
public class SubscriptionController {

    @Autowired
    private  OrderService orderService;

    @Autowired
    private  MilkOrderRepository milkOrderRepository;

//    public SubscriptionController(OrderService orderService,
//                                  MilkOrderRepository milkOrderRepository) {
//        this.orderService = orderService;
//        this.milkOrderRepository = milkOrderRepository;
//    }

    @PostMapping("/api/subscribe")
        public ResponseEntity<MilkOrder> subscribe(@RequestBody SubscribeRequest req) {
        MilkOrder created = orderService.subscribeAndCreateOrder(req);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/api/orders")
    public List<MilkOrder> list() {
        return milkOrderRepository.findAll();
    }
}
